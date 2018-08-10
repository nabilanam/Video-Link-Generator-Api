package com.nabilanam.downloader.youtube.util;

import com.nabilanam.downloader.youtube.cipher.CipherOperation;
import com.nabilanam.downloader.youtube.cipher.ReverseCipherOperation;
import com.nabilanam.downloader.youtube.cipher.SliceCipherOperation;
import com.nabilanam.downloader.youtube.cipher.SwapCipherOperation;
import com.nabilanam.downloader.youtube.model.MuxedStream;
import com.nabilanam.downloader.youtube.model.PlayerSource;
import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Data
@Component
public class MuxedStreamUtils {

	private final StreamUtils streamUtils;
	private final RegexUtil regexUtil;
	private final HttpFileReader httpFileReader;

	@Autowired
	public MuxedStreamUtils(StreamUtils streamUtils, RegexUtil regexUtil, HttpFileReader httpFileReader) {
		this.streamUtils = streamUtils;
		this.regexUtil = regexUtil;
		this.httpFileReader = httpFileReader;
	}

	public List<MuxedStream> getMuxedStreams(String videoId) throws Exception {
		String embedPageUrl = getEmbedPageUrl(videoId);

		Document jsoupDocument = getJsoupDocument(embedPageUrl);
		String sts = getPlayerSessionSts(jsoupDocument);
		String url = getVideoInfoUrl(videoId, "en", sts, null);
		HashMap<String, String> queryMap = getQueryMap(url);

		String fmtStreamMapData = getFmtStreamMapData(queryMap);
		String errorCode = "";
		if (fmtStreamMapData == null || fmtStreamMapData.isEmpty()) {
			for (String key : queryMap.keySet()) {
				if (key.equals("errorcode")) {
					errorCode = queryMap.get(key);
					break;
				}
			}
			if (errorCode.equals("150") || errorCode.equals("100")) {
				String eurl = "https://youtube.googleapis.com/v/" + videoId;
				url = getVideoInfoUrl(videoId, "detailpage", sts, eurl);
				queryMap = getQueryMap(url);
				fmtStreamMapData = getFmtStreamMapData(queryMap);
			}
		}
		String title = getTitle(queryMap);
		String thumbanilUrl = getThumbanilUrl(queryMap);
		return getPopulatedMuxedStreams(jsoupDocument, fmtStreamMapData, title, thumbanilUrl);
	}

	private List<MuxedStream> getPopulatedMuxedStreams(Document jsoupDocument, String fmtStreamMapData, String title, String thumbnailUrl) throws Exception {
		List<MuxedStream> muxedStreams = new ArrayList<>();
		if (!fmtStreamMapData.isEmpty()) {

			String srcUrl = null;
			PlayerSource playerSource = null;
			String[] rawMuxedStreams = fmtStreamMapData.split(",");

			for (String rawMuxedStream : rawMuxedStreams) {
				String[] properties = getDecodedStreamProperties(rawMuxedStream);
				MuxedStream muxedStream = new MuxedStream();
				String tagItag = "itag=";
				String tagUrl = "url=";
				String tagSignature = "s=";
				for (String property : properties) {
					if (property.startsWith(tagItag)) {
						int itag = Integer.parseInt(property.substring(tagItag.length()));
						muxedStream.setItag(itag);
						muxedStream.setItagDescriptor(streamUtils.getItagDescriptor(itag));
					} else if (property.startsWith(tagUrl)) {
						muxedStream.setUrl(URLDecoder.decode(property.substring(tagUrl.length()), "UTF-8"));
					} else if (property.startsWith(tagSignature)) {
						if (srcUrl == null) {
							srcUrl = getBaseJsUrl(jsoupDocument);
							playerSource = getPlayerSource(srcUrl);
						}
						muxedStream.setSignature(property.substring(tagSignature.length()));
						muxedStream.setSignature(playerSource.decipher(muxedStream.getSignature()));
					}
				}
				muxedStream.setTitle(title);
				muxedStream.setThumbnailUrl(thumbnailUrl);
				muxedStreams.add(muxedStream);
			}
		}
		return muxedStreams;
	}

	private String getTitle(HashMap<String, String> queryMap) {
		String title = "";
		String titleKey = "title";
		title = queryMap.get(titleKey);
		title = title.replace("+", " ");
		return title;
	}

	private String getThumbanilUrl(HashMap<String, String> queryMap) {
		String url = "";
		String urlKey = "thumbnail_url";
		url = queryMap.get(urlKey);
		return url;
	}

	private String getFmtStreamMapData(HashMap<String, String> queryMap) {
		String fmtStreamMapData = "";
		String fmtStreamMapKey = "url_encoded_fmt_stream_map";
		fmtStreamMapData = queryMap.get(fmtStreamMapKey);
		return fmtStreamMapData;
	}

	private String getVideoInfoUrl(String videoId, String hl, String sts, String eurl) {
		if (eurl == null)
			return "https://www.youtube.com/get_video_info?video_id=" + videoId + "&sts=" + sts + "&hl=" + hl;
		else
			return "https://www.youtube.com/get_video_info?video_id=" + videoId + "&sts=" + sts + "&eurl=" + eurl + "&hl=" + hl;
	}

	private PlayerSource getPlayerSource(String srcUrl) throws Exception {
		String srcData = httpFileReader.readDataFromUrlResource(srcUrl);

		String jsFuncName = getJsFuncName(srcData);
		Optional<String> groupOne;

		String[] jsFuncLines = getJsFuncLines(srcData, jsFuncName);

		String reverseFuncName = null;
		String sliceFuncName = null;
		String charSwapFuncName = null;

		//get name of cipher operations
		for (String line : jsFuncLines) {
			if (reverseFuncName != null && sliceFuncName != null && charSwapFuncName != null) {
				break;
			}
			//get func called in this line
			String calledFuncName = "";
			groupOne = regexUtil.getGroupOne(line, "\\w+\\.(\\w+)\\(");
			if (groupOne.isPresent()) {
				calledFuncName = groupOne.get();
			}
			if (calledFuncName.isEmpty()) continue;

			//find cipher func names
			if (regexUtil.isMatch(srcData, calledFuncName + ":\\bfunction\\b\\(\\w+\\)")) {
				reverseFuncName = calledFuncName;
			} else if (regexUtil.isMatch(srcData, calledFuncName + ":\\bfunction\\b\\([a],b\\).(\\breturn\\b)?.?\\w+\\.")) {
				sliceFuncName = calledFuncName;
			} else if (regexUtil.isMatch(srcData, calledFuncName + ":\\bfunction\\b\\(\\w+\\,\\w\\).\\bvar\\b.\\bc=a\\b")) {
				charSwapFuncName = calledFuncName;
			}
		}

		//get func operation set and order
		List<CipherOperation> operations = getPopulatedCipherOperations(jsFuncLines, reverseFuncName, sliceFuncName, charSwapFuncName);
		return new PlayerSource(operations);
	}

	private String[] getJsFuncLines(String srcData, String jsFuncName) throws Exception {
		Optional<String> groupOne;
		String jsFuncBody = "";
		groupOne = regexUtil.getGroupOne(srcData, "(?!h\\.)" + jsFuncName + "=function\\(\\w+\\)\\{(.*?)\\}");
		if (groupOne.isPresent()) {
			jsFuncBody = groupOne.get();
			if (jsFuncBody.isEmpty()) {
				throw new Exception("Js method body empty!");
			}
		}
		return jsFuncBody.split(";");
	}

	private String getJsFuncName(String srcData) throws Exception {
		String jsFuncName = "";
		Optional<String> groupOne = regexUtil.getGroupOne(srcData, "\"signature\",\\s?([a-zA-Z0-9$]+)\\(");
		if (groupOne.isPresent()) {
			jsFuncName = groupOne.get();
			if (jsFuncName.isEmpty()) {
				throw new Exception("Js method name empty!");
			}
		}
		return jsFuncName;
	}

	private List<CipherOperation> getPopulatedCipherOperations(String[] jsFuncLines, String reverseFuncName, String sliceFuncName, String charSwapFuncName) {
		Optional<String> groupOne;
		List<CipherOperation> operations = new ArrayList<>();
		for (String line : jsFuncLines) {
			groupOne = regexUtil.getGroupOne(line, "\\w+\\.(\\w+)\\(");
			String calledFuncName = "";
			if (groupOne.isPresent()) {
				calledFuncName = groupOne.get();
			}
			if (calledFuncName.isEmpty()) continue;
			if (calledFuncName.equals(charSwapFuncName)) {
				groupOne = regexUtil.getGroupOne(line, "\\(\\w+,(\\d+)\\)");
				if (groupOne.isPresent()) {
					int index = Integer.parseInt(groupOne.get());
					operations.add(new SwapCipherOperation(index));
				}
			} else if (calledFuncName.equals(sliceFuncName)) {
				groupOne = regexUtil.getGroupOne(line, "\\(\\w+,(\\d+)\\)");
				if (groupOne.isPresent()) {
					int index = Integer.parseInt(groupOne.get());
					operations.add(new SliceCipherOperation(index));
				}
			} else if (calledFuncName.equals(reverseFuncName)) {
				operations.add(new ReverseCipherOperation());
			}
		}
		return operations;
	}

	private String[] getDecodedStreamProperties(String rawMuxedStream) {
		rawMuxedStream = rawMuxedStream.replace("%22", "\"");
		rawMuxedStream = rawMuxedStream.replace("%2C", ",");
		rawMuxedStream = rawMuxedStream.replace("%2F", "/");
		rawMuxedStream = rawMuxedStream.replace("%3B", ";");
		rawMuxedStream = rawMuxedStream.replace("%3D", "=");
		return rawMuxedStream.split("&");
	}

	private HashMap<String, String> getQueryMap(String infoUrl) throws IOException {
		String encodedData = httpFileReader.readDataFromUrlResource(infoUrl);
		return createQueryMapFromData(encodedData);
	}

	private Document getJsoupDocument(String url) throws IOException {
		return Jsoup.connect(url).get();
	}

	private String getPlayerSessionSts(Document document) {
		Elements scripts = document.getElementsByTag("script");

		int sts = 0;
		boolean found = false;
		for (Element script : scripts) {
			List<DataNode> dataNodes = script.dataNodes();
			for (DataNode node : dataNodes) {
				if (node.getWholeData().startsWith("yt.setConfig")) {
					String wholeData = node.getWholeData();
					String stsStr = "\"sts\":";
					int index = wholeData.indexOf("\"sts\":");
					wholeData = wholeData.substring(index);
					int endIndex = wholeData.length();
					int commaIndex = wholeData.indexOf(",");
					int braceIndex = wholeData.indexOf("}");
					if ((commaIndex > -1) && (braceIndex > -1)) {
						if (commaIndex < braceIndex) {
							endIndex = commaIndex;
						} else endIndex = braceIndex;
					} else if (commaIndex > -1) {
						endIndex = commaIndex;
					} else if (braceIndex > -1) {
						endIndex = braceIndex;
					}
					sts = Integer.parseInt(wholeData.substring(stsStr.length(), endIndex));
					found = true;
					break;
				}
			}
			if (found) break;
		}

		return "" + sts;
	}

	private String getBaseJsUrl(Document document) {
		Elements scripts = document.getElementsByTag("script");
		String src = "";
		for (Element script : scripts) {
			String name = script.attr("name");
			if (name.equals("player/base")) {
				src = script.attr("src");
			}
		}
		return "https://www.youtube.com" + src;
	}

	private String getEmbedPageUrl(String videoId) {
		return "https://www.youtube.com/embed/" + videoId;
	}

	private HashMap<String, String> createQueryMapFromData(String queryString) throws UnsupportedEncodingException {
		HashMap<String, String> map = new HashMap<>();
		String[] fields = queryString.split("&");
		for (String field : fields) {
			String[] pair = field.split("=");
			if (pair.length == 2) {
				String key = pair[0];
				String value = URLDecoder.decode(pair[1], "UTF-8").replace('+', ' ');
				map.put(key, value);
			}
		}
		return map;
	}
}

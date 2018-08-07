package com.nabilanam.api.uselessapis.service.downloadlink.youtube;

import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.*;
import java.net.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class YoutubeUtils implements ApplicationRunner {

	//extract fmt urls
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println("###");
		Optional<HashMap<String, String>> mapOptional = getUrlEncodedFmtStreamMap("4G4e42EG4vg");
		System.out.println("###");
	}

	Optional<HashMap<String, String>> getUrlEncodedFmtStreamMap(String videoId) throws IOException {
		String[] srcUrlAndSts = getPlayerSourceUrlAndSts(videoId);
		String srcUrl = srcUrlAndSts[0];
		String sts = srcUrlAndSts[1];
		String url = "https://www.youtube.com/get_video_info?video_id=" + videoId + "&sts=" + sts + "&hl=en";
		HashMap<String, String> hashMap = getQueryMap(url);
		String data = "";
		String fmtStreamMapKey= "url_encoded_fmt_stream_map";
		for (String key : hashMap.keySet()) {
			if (key.equals(fmtStreamMapKey)){
				data = hashMap.get(key);
				break;
			}
		}
		HashMap<String, String> map = null;
		if (!data.isEmpty()){
			String[] streams = data.split(",");
			for (String str : streams){
				String[] properties = str.split("&");
				for (String property : properties){
					System.out.println("###");
					System.out.println(property);
					System.out.println("###");
					//01785684111//
				}
				System.out.println("##############");
			}
		}
		return Optional.ofNullable(map);
	}

	HashMap<String, String> getQueryMap(String url) throws IOException {
		String encodedData = getVideoInfo(url);
		return createQueryMapFromData(encodedData);
	}

	String getVideoInfo(String url) throws IOException {
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		con.setInstanceFollowRedirects(true);
		con.setRequestMethod("GET");
		con.setRequestProperty("User-Agent",
				"Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/51.0.2704.103 Safari/537.36");
		int code = con.getResponseCode();
		StringBuilder sb = new StringBuilder();
		if (code == HttpURLConnection.HTTP_OK) {
			try (InputStream stream = new BufferedInputStream(con.getInputStream())) {
				try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
					String line;
					while ((line = reader.readLine()) != null) {
						sb.append(line);
					}
				}
			}
		}
		//else el=detailpage
		return sb.toString();
	}

	String[] getPlayerSourceUrlAndSts(String videoId) throws IOException {
		String embedPageUrl = getEmbedPageUrl(videoId);
		Document document = Jsoup.connect(embedPageUrl).get();
		Elements scripts = document.getElementsByTag("script");
		String src = "";
		for (Element script : scripts) {
			String name = script.attr("name");
			if (name.equals("player/base")){
				src = script.attr("src");
			}
		}
		String url ="https://www.youtube.com"+src;

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

		return new String[]{url,""+sts};
	}

	String getEmbedPageUrl(String videoId) throws MalformedURLException {
		return "https://www.youtube.com/embed/"+videoId;
	}

	HashMap<String, String> createQueryMapFromData(String queryString) throws UnsupportedEncodingException {
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

	private final YoutubeIdFinder idFinder;

	@Autowired
	public YoutubeUtils(YoutubeIdFinder idFinder) {
		this.idFinder = idFinder;
	}

	String parseVideoId(@NotNull String url) {
		if (url.isEmpty()) return "";

		String regularUrlRegex = ".+youtube\\..+?/watch.*?v=(.*?)(?:&|/|$)";
		String shortUrlRegex = ".+youtu\\.be/(.*?)(?:\\?|&|/|$)";
		String embedUrlRegex = ".+youtube\\..+?/embed/(.*?)(?:\\?|&|/|$)";

		// https://www.youtube.com/watch?v=yIVRs6YSbOM
		String videoId = idFinder.findVideoId(url, regularUrlRegex);

		// https://youtu.be/yIVRs6YSbOM
		if (videoId.isEmpty()) {
			videoId = idFinder.findVideoId(url, shortUrlRegex);
		}

		// https://www.youtube.com/embed/yIVRs6YSbOM
		if (videoId.isEmpty()) {
			videoId = idFinder.findVideoId(url, embedUrlRegex);
		}

		return videoId;
	}

	String parsePlaylistId(String url) {
		if (url.isEmpty()) return "";

		String regularUrlRegex = ".+youtube\\..+?/playlist.*?list=(.*?)(?:&|/|$)";
		String compositeUrlRegex = ".+youtube\\..+?/watch.*?list=(.*?)(?:&|/|$)";
		String shortCompositeUrlRegex = ".+youtu\\.be/.*?/.*?list=(.*?)(?:&|/|$)";
		String embedCompositeUrlRegex = ".+youtube\\..+?/embed/.*?/.*?list=(.*?)(?:&|/|$)";

		// https://www.youtube.com/playlist?list=PLOU2XLYxmsIJGErt5rrCqaSGTMyyqNt2H
		String videoId = idFinder.findPlaylistId(url, regularUrlRegex);

		// https://www.youtube.com/watch?v=b8m9zhNAgKs&list=PL9tY0BWXOZFuFEG_GtOBZ8-8wbkH-NVAr
		if (videoId.isEmpty()) {
			videoId = idFinder.findPlaylistId(url, compositeUrlRegex);
		}

		// https://youtu.be/b8m9zhNAgKs/?list=PL9tY0BWXOZFuFEG_GtOBZ8-8wbkH-NVAr
		if (videoId.isEmpty()) {
			videoId = idFinder.findPlaylistId(url, shortCompositeUrlRegex);
		}

		// https://www.youtube.com/embed/b8m9zhNAgKs/?list=PL9tY0BWXOZFuFEG_GtOBZ8-8wbkH-NVAr
		if (videoId.isEmpty()) {
			videoId = idFinder.findPlaylistId(url, embedCompositeUrlRegex);
		}

		return videoId;
	}

//	Optional<Integer> getSts(String videoUrl) throws IOException {
//		Integer sts = null;
//		boolean found = false;
//		Document document = Jsoup.connect(videoUrl).get();
//		Elements scripts = document.getElementsByTag("script");
//		for (Element script : scripts) {
//			List<DataNode> dataNodes = script.dataNodes();
//			for (DataNode node : dataNodes) {
//				if (node.getWholeData().startsWith("var ytplayer")) {
//					String wholeData = node.getWholeData();
//					String stsStr = "\"sts\":";
//					int index = wholeData.indexOf("\"sts\":");
//					wholeData = wholeData.substring(index);
//					int endIndex = wholeData.length();
//					int commaIndex = wholeData.indexOf(",");
//					int braceIndex = wholeData.indexOf("}");
//					if ((commaIndex > -1) && (braceIndex > -1)) {
//						if (commaIndex < braceIndex) {
//							endIndex = commaIndex;
//						} else endIndex = braceIndex;
//					} else if (commaIndex > -1) {
//						endIndex = commaIndex;
//					} else if (braceIndex > -1) {
//						endIndex = braceIndex;
//					}
//					sts = Integer.parseInt(wholeData.substring(stsStr.length(), endIndex));
//					found = true;
//					break;
//				}
//			}
//			if (found) break;
//		}
//		return Optional.ofNullable(sts);
//	}
}

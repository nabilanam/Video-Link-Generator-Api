package com.nabilanam.downloader.facebook;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.downloader.shared.contract.VideoStreamProvider;
import com.nabilanam.downloader.shared.model.Container;
import com.nabilanam.downloader.shared.model.VideoStream;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import com.nabilanam.downloader.shared.util.RegexUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class FacebookStreamProvider implements VideoStreamProvider {

	private final RegexUtil regexUtil;

	@Autowired
	public FacebookStreamProvider(RegexUtil regexUtil) {
		this.regexUtil = regexUtil;
	}

	public VideoStreamContainer getVideoStreamContainer(URL url) throws ResourceNotFoundException {
		VideoStreamContainer container;
		try {
			Document document = getVideoDocument(url);
			String script = getTimeSliceScript(document);
			List<VideoStream> streams = getFacebookStream(script);
			String title = getTitle(document.body().html());
			String thumbnailUrl = getThumbnailUrl(document.body().html());
			container = new VideoStreamContainer(title, thumbnailUrl, streams);
		} catch (Exception ex) {
			throw new ResourceNotFoundException();
		}
		return container;
	}

	private List<VideoStream> getFacebookStream(String script) {
		Optional<String> groupOne = regexUtil.getGroupOne(script, "(\"sd_src_no_ratelimit\":.+?)(?=,\"hd_tag\")");
		List<VideoStream> streams = new ArrayList<>();
		if (groupOne.isPresent()) {
			String data = groupOne.get();
			HashMap<String, String> map = createQueryMapFromData(data);
			for (String key : map.keySet()) {
				if (key.equals("hd_src")) {
					VideoStream videoStream = new VideoStream(map.get(key), "HD", Container.mp4);
					streams.add(videoStream);
				} else if (key.equals("sd_src")) {
					VideoStream videoStream = new VideoStream(map.get(key), "SD", Container.mp4);
					streams.add(videoStream);
				}
			}
			System.out.println(streams);
		}
		return streams;
	}

	private HashMap<String, String> createQueryMapFromData(String queryString) {
		HashMap<String, String> map = new HashMap<>();
		String[] fields = queryString.split(",");
		for (String field : fields) {
			String[] pair = field.split("\":\"");
			if (pair.length == 2) {
				String key = pair[0].replace("\"", "");
				String value = pair[1].replace("\\", "");
				value = value.replace("\"", "");
				map.put(key, value);
			}
		}
		return map;
	}

	private String getTimeSliceScript(Document document) {
		Elements scripts = document.getElementsByTag("script");
		for (Element script : scripts) {
			for (DataNode node : script.dataNodes()) {
				if (node.getWholeData().startsWith("require(\"TimeSlice\")")) {
					return node.getWholeData();
				}
			}
		}
		return "";
	}

	private String getTitle(String html) {
		String regex = "\"_50f7\">(.+?)(?=<)";
		Optional<String> groupOne = regexUtil.getGroupOne(html, regex);
		return groupOne.orElse("");
	}

	private String getThumbnailUrl(String html) {
		String regex = "\"_1p6f[\\s\\S]+?(?==)=\"(.+?)(?=\")";
		Optional<String> groupOne = regexUtil.getGroupOne(html, regex);
		String url = "";
		if (groupOne.isPresent()) {
			url = groupOne.get().replace(";", "&");
		}
		return url;
	}

	private Document getVideoDocument(URL url) throws IOException {
		String customizedUrl = getCustomizedVideoPluginUrl(url.toString());
		return Jsoup.connect(customizedUrl).get();
	}

	private String getCustomizedVideoPluginUrl(String url) {
		return "https://www.facebook.com/plugins/video.php?href=" + url;
	}
}

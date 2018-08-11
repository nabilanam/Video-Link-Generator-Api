package com.nabilanam.downloader.facebook;

import com.nabilanam.downloader.facebook.model.FacebookStream;
import com.nabilanam.downloader.facebook.model.VideoQuality;
import com.nabilanam.downloader.util.RegexUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Component
public class FacebookClient {

	private final RegexUtil regexUtil;

	@Autowired
	public FacebookClient(RegexUtil regexUtil) {
		this.regexUtil = regexUtil;
	}

	public List<FacebookStream> getDownloadLink(URL url) throws IOException {
		Document document = getVideoDocument(url);
		String script = getTimeSliceScript(document);
		return getFacebookStream(script);
	}

	private List<FacebookStream> getFacebookStream(String script) throws MalformedURLException {
		Optional<String> groupOne = regexUtil.getGroupOne(script, "(\"sd_src_no_ratelimit\":.+?)(?=,\"hd_tag\")");
		FacebookStream stream;
		List<FacebookStream> streams = new ArrayList<>();
		if (groupOne.isPresent()) {
			String data = groupOne.get();
			HashMap<String, String> map = createQueryMapFromData(data);
			for (String key : map.keySet()) {
				if (key.equals("hd_src")) {
					stream = new FacebookStream(new URL(map.get(key)), VideoQuality.HD);
					streams.add(stream);
				} else if (key.equals("sd_src")) {
					stream = new FacebookStream(new URL(map.get(key)), VideoQuality.SD);
					streams.add(stream);
				}
			}
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

	private Document getVideoDocument(URL url) throws IOException {
		String customizedUrl = getCustomizedVideoPluginUrl(url.toString());
		return Jsoup.connect(customizedUrl).get();
	}

	private String getCustomizedVideoPluginUrl(String url) {
		return "https://www.facebook.com/plugins/video.php?href=" + url;
	}
}

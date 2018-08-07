package com.nabilanam.api.uselessapis.service.downloadlink.youtube;

import lombok.Data;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Data
@Component
public class YoutubeClient {

	private final YoutubeIdFinder idFinder;

	@Autowired
	public YoutubeClient(YoutubeIdFinder idFinder) {
		this.idFinder = idFinder;
	}

	Optional<Integer> getSts(String videoUrl) throws IOException {
		Integer sts = null;
		boolean found = false;
		Document document = Jsoup.connect(videoUrl).get();
		Elements scripts = document.getElementsByTag("script");
		for (Element script : scripts) {
			List<DataNode> dataNodes = script.dataNodes();
			for (DataNode node : dataNodes) {
				if (node.getWholeData().startsWith("var ytplayer")) {
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
		return Optional.ofNullable(sts);
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

}

package com.nabilanam.api.uselessapis.service.downloadlink.youtube;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;

@Data
@Component
public class YoutubeClient {

	@Autowired
	private YoutubeIdFinder idFinder;

	private String generateEmbedUrl(String videoId){
		return "https://www.youtube.com/embed/"+videoId;
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
			videoId = idFinder.findVideoId(url,shortUrlRegex);
		}

		// https://www.youtube.com/embed/yIVRs6YSbOM
		if (videoId.isEmpty()) {
			videoId = idFinder.findVideoId(url,embedUrlRegex);
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

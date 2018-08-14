package com.nabilanam.downloader.youtube.util;

import com.nabilanam.downloader.shared.contract.IdentityParser;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Component
public class YoutubeIdentityParser implements IdentityParser {

	public String parseTrackId(String url) throws Exception {
		if (url.isEmpty())
			throw new Exception();

		String regularUrlRegex = ".+youtube\\..+?/watch.*?v=(.*?)(?:&|/|$)";
		String shortUrlRegex = ".+youtu\\.be/(.*?)(?:\\?|&|/|$)";
		String embedUrlRegex = ".+youtube\\..+?/embed/(.*?)(?:\\?|&|/|$)";

		// https://www.youtube.com/watch?v=yIVRs6YSbOM
		String videoId = findVideoId(url, regularUrlRegex);

		// https://youtu.be/yIVRs6YSbOM
		if (videoId.isEmpty()) {
			videoId = findVideoId(url, shortUrlRegex);
		}

		// https://www.youtube.com/embed/yIVRs6YSbOM
		if (videoId.isEmpty()) {
			videoId = findVideoId(url, embedUrlRegex);
		}

		if (videoId.isEmpty())
			throw new Exception();

		return videoId;
	}

	public String parsePlaylistId(String url) throws Exception {
		if (url.isEmpty())
			throw new Exception();

		String regularUrlRegex = ".+youtube\\..+?/playlist.*?list=(.*?)(?:&|/|$)";
		String compositeUrlRegex = ".+youtube\\..+?/watch.*?list=(.*?)(?:&|/|$)";
		String shortCompositeUrlRegex = ".+youtu\\.be/.*?/.*?list=(.*?)(?:&|/|$)";
		String embedCompositeUrlRegex = ".+youtube\\..+?/embed/.*?/.*?list=(.*?)(?:&|/|$)";

		// https://www.youtube.com/playlist?list=PLOU2XLYxmsIJGErt5rrCqaSGTMyyqNt2H
		String videoId = findPlaylistId(url, regularUrlRegex);

		// https://www.youtube.com/watch?v=b8m9zhNAgKs&list=PL9tY0BWXOZFuFEG_GtOBZ8-8wbkH-NVAr
		if (videoId.isEmpty()) {
			videoId = findPlaylistId(url, compositeUrlRegex);
		}

		// https://youtu.be/b8m9zhNAgKs/?list=PL9tY0BWXOZFuFEG_GtOBZ8-8wbkH-NVAr
		if (videoId.isEmpty()) {
			videoId = findPlaylistId(url, shortCompositeUrlRegex);
		}

		// https://www.youtube.com/embed/b8m9zhNAgKs/?list=PL9tY0BWXOZFuFEG_GtOBZ8-8wbkH-NVAr
		if (videoId.isEmpty()) {
			videoId = findPlaylistId(url, embedCompositeUrlRegex);
		}

		if (videoId.isEmpty())
			throw new Exception();

		return videoId;
	}

	private String findVideoId(String url, String regex) {
		String id = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		if (matcher.matches()) {
			id = matcher.group(1);
			if (!validateVideoId(id)) {
				id = "";
			}
		}
		return id;
	}

	private boolean validateVideoId(String id) {
		if (id.isEmpty())
			return false;

		String regex = "[^0-9a-zA-Z_\\-]";
		if (id.length() != 11)
			return false;

		return !id.matches(regex);
	}

	private String findPlaylistId(String url, String regex) {
		String id = "";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		if (matcher.matches()) {
			id = matcher.group(1);
			if (!validatePlaylistId(id)) {
				id = "";
			}
		}
		return id;
	}

	private boolean validatePlaylistId(String id) {
		if (id.isEmpty())
			return false;

		// Watch later playlist is special
		if (id.equals("WL"))
			return true;

		// Other playlist IDs should start with these two characters
		if (!id.startsWith("PL") &&
				!id.startsWith("RD") &&
				!id.startsWith("UL") &&
				!id.startsWith("UU") &&
				!id.startsWith("PU") &&
				!id.startsWith("LL") &&
				!id.startsWith("FL"))
			return false;

		// Playlist IDs vary a lot in length()s, so we will just compare with the extremes
		if (id.length() < 13 || id.length() > 34)
			return false;

		String regex = "[^0-9a-zA-Z_\\-]";
		return !id.matches(regex);
	}
}

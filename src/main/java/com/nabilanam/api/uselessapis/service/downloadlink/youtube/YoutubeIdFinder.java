package com.nabilanam.api.uselessapis.service.downloadlink.youtube;

import lombok.Data;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Data
@Component
public class YoutubeIdFinder {

	String findVideoId(String url, String regex) {
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

	private boolean validateVideoId(@NotNull String id) {
		if (id.isEmpty())
			return false;

		String regex = "[^0-9a-zA-Z_\\-]";
		if (id.length() != 11)
			return false;

		return !id.matches(regex);
	}

	String findPlaylistId(String url, String regex) {
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

	private boolean validatePlaylistId(@NotNull String id) {
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

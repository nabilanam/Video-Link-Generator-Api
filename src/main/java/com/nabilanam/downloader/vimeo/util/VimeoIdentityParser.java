package com.nabilanam.downloader.vimeo.util;

import com.nabilanam.downloader.shared.contract.IdentityParser;
import com.nabilanam.downloader.shared.util.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class VimeoIdentityParser implements IdentityParser {

	private final RegexUtil regexUtil;

	@Autowired
	public VimeoIdentityParser(RegexUtil regexUtil) {
		this.regexUtil = regexUtil;
	}

	@Override
	public String parseTrackId(String url) throws Exception {
		String regex = "https://vimeo.com/(\\d+?)(?=\\b)";
		Optional<String> groupOne = regexUtil.getGroupOne(url, regex);
		if (groupOne.isPresent())
			return groupOne.get();
		else {
			regex = "https://vimeo.com/\\w+/\\w+/(\\d+?)(?=\\b)";
			groupOne = regexUtil.getGroupOne(url, regex);
			if (groupOne.isPresent()) {
				return groupOne.get();
			} else throw new Exception();
		}
	}
}

package com.nabilanam.downloader.twitter.util;

import com.nabilanam.downloader.util.RegexUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class StatusIdentityParser {

	private final
	RegexUtil regexUtil;

	@Autowired
	public StatusIdentityParser(RegexUtil regexUtil) {
		this.regexUtil = regexUtil;
	}

	public String parseId(String url) {
		String regex = "https://twitter.com/\\w+/\\w+/(\\d+)";
		Optional<String> groupOne = regexUtil.getGroupOne(url, regex);
		return groupOne.orElse("");
	}
}

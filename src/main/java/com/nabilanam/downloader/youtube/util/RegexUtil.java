package com.nabilanam.downloader.youtube.util;

import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexUtil {

	public boolean isMatch(String input, String regex){
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		return matcher.find();
	}

	public Optional<String> getGroupOne(String input, String regex){
		String group = null;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(input);
		if (matcher.find())
			group = matcher.group(1);
		return Optional.ofNullable(group);
	}
}

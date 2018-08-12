package com.nabilanam.downloader.twitter.model.twitterapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.net.URL;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Variant {

	private long bitrate;
	private String content_type;
	private URL url;
}

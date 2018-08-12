package com.nabilanam.downloader.twitter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URL;

@Data
public class TwitterStream {

	private Container container;
	private String resolution;
	private URL url;
}

package com.nabilanam.downloader.twitter.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URL;
import java.util.List;

@Data
public class TwitterStreamsWrapper {
	private String text;
	private URL thumbnailUrl;
	private List<TwitterStream> streams;
}

package com.nabilanam.downloader.youtube.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class YoutubeStream {

	private String title;
	private String url;
	private String thumbnailUrl;
	private String container;
	private String videoQuality;
}

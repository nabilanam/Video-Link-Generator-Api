package com.nabilanam.downloader.youtube.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
public class YoutubeStream {

	private String url;
	private String container;
	private String videoQuality;
}

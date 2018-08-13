package com.nabilanam.downloader.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoStream {

	private String url;
	private String quality;
	private Container container;
}

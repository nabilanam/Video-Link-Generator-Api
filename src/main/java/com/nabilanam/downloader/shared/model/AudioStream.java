package com.nabilanam.downloader.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AudioStream {

	private String title;
	private String thumbUrl;
	private String url;
}

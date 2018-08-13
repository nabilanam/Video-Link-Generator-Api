package com.nabilanam.downloader.soundcloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SoundCloudStream {

	private String title;
	private String thumbnailUrl;
	private String url;
}

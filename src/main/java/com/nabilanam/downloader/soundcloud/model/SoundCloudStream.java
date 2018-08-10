package com.nabilanam.downloader.soundcloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URL;

@Data
@AllArgsConstructor
public class SoundCloudStream {

	private String title;
	private URL url;
}

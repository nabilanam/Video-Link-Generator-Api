package com.nabilanam.downloader.soundcloud.model;

import lombok.Data;

import java.net.URL;

@Data
public class SoundCloudStream {
	private URL http_mp3_128_url;
	private URL hls_mp3_128_url;
	private URL hls_opus_64_url;
	private URL preview_mp3_128_url;
}

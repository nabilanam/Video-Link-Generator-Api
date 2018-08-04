package com.nabilanam.api.uselessapis.model.DownloadLink;

import lombok.Data;

import java.net.URL;

@Data
public class SoundCloudApi {
	private URL http_mp3_128_url;
	private URL hls_mp3_128_url;
	private URL hls_opus_64_url;
	private URL preview_mp3_128_url;
}

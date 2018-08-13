package com.nabilanam.downloader.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.net.URL;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Media {

	private URL media_url_https;
	private VideoInfo video_info;
}

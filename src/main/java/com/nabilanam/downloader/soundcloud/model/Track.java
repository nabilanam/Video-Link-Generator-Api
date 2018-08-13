package com.nabilanam.downloader.soundcloud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.net.URL;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {
	private long id;
	private String title;
	private String artwork_url;
}

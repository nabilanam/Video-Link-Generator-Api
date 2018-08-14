package com.nabilanam.downloader.vimeo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Progressive {
	private String mime;
	private String url;
	private String quality;
}

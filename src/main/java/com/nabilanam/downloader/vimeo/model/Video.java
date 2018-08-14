package com.nabilanam.downloader.vimeo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {

	private Thumb thumbs;
	private String title;
}

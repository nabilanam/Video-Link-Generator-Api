package com.nabilanam.downloader.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VideoInfo {

	private long duration_millis;
	private List<Variant> variants;
}

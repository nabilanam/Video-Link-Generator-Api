package com.nabilanam.downloader.facebook.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FacebookStreamsWrapper {

	private String title;
	private String thumbnailUrl;
	private List<FacebookStream> streams;
}

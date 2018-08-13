package com.nabilanam.downloader.youtube.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class YoutubeStreamsWrapper {

	private String title;
	private String thumbnailUrl;
	private List<YoutubeStream> streams;
}

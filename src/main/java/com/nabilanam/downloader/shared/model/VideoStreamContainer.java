package com.nabilanam.downloader.shared.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class VideoStreamContainer {

	private String title;
	private String thumbUrl;
	private List<VideoStream> streams;
}

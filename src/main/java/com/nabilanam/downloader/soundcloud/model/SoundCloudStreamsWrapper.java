package com.nabilanam.downloader.soundcloud.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class SoundCloudStreamsWrapper {

	private String title;
	private String thumbnailUrl;
	private List<SoundCloudStream> streams;
}

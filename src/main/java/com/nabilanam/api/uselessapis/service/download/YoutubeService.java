package com.nabilanam.api.uselessapis.service.download;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.api.uselessapis.service.download.contract.VideoService;
import com.nabilanam.downloader.shared.contract.VideoStreamProvider;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class YoutubeService implements VideoService {

	private final VideoStreamProvider youtubeStreamProvider;

	@Autowired
	public YoutubeService(VideoStreamProvider youtubeStreamProvider) {
		this.youtubeStreamProvider = youtubeStreamProvider;
	}

	public VideoStreamContainer getVideoStreamContainer(URL trackUrl) throws ResourceNotFoundException {
		return youtubeStreamProvider.getVideoStreamContainer(trackUrl);
	}
}

package com.nabilanam.api.uselessapis.service.download;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.api.uselessapis.service.download.contract.VideoService;
import com.nabilanam.downloader.shared.contract.VideoStreamProvider;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class TwitterService implements VideoService {

	private final VideoStreamProvider twitterStreamProvider;

	@Autowired
	public TwitterService(VideoStreamProvider twitterStreamProvider) {
		this.twitterStreamProvider = twitterStreamProvider;
	}

	public VideoStreamContainer getVideoStreamContainer(URL url) throws ResourceNotFoundException {
		return twitterStreamProvider.getVideoStreamContainer(url);
	}
}

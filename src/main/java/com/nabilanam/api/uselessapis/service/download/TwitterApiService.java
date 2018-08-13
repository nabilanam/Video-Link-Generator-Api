package com.nabilanam.api.uselessapis.service.download;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import com.nabilanam.downloader.shared.model.VideoStreamProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class TwitterApiService {

	private final VideoStreamProvider twitterStreamProvider;

	@Autowired
	public TwitterApiService(VideoStreamProvider twitterStreamProvider) {
		this.twitterStreamProvider = twitterStreamProvider;
	}

	public VideoStreamContainer getTwitterVideoStreamContainer(URL url) throws ResourceNotFoundException {
		return twitterStreamProvider.getVideoStreamContainer(url);
	}
}

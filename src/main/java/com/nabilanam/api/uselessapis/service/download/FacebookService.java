package com.nabilanam.api.uselessapis.service.download;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.api.uselessapis.service.download.contract.VideoService;
import com.nabilanam.downloader.shared.contract.VideoStreamProvider;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
public class FacebookService implements VideoService {

	private final VideoStreamProvider facebookStreamProvider;

	@Autowired
	public FacebookService(VideoStreamProvider facebookStreamProvider) {
		this.facebookStreamProvider = facebookStreamProvider;
	}

	public VideoStreamContainer getVideoStreamContainer(URL url) throws ResourceNotFoundException {
		return facebookStreamProvider.getVideoStreamContainer(url);
	}
}

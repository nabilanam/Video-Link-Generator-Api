package com.nabilanam.api.uselessapis.service.download;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import com.nabilanam.downloader.shared.model.VideoStreamProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class FacebookApiService {

	private final VideoStreamProvider facebookStreamProvider;

	@Autowired
	public FacebookApiService(VideoStreamProvider facebookStreamProvider) {
		this.facebookStreamProvider = facebookStreamProvider;
	}

	public VideoStreamContainer getFacebookVideoStreamContainer(URL url) throws ResourceNotFoundException {
		return facebookStreamProvider.getVideoStreamContainer(url);
	}
}

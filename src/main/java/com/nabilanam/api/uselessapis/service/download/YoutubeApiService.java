package com.nabilanam.api.uselessapis.service.download;

import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import com.nabilanam.downloader.shared.model.VideoStreamProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class YoutubeApiService {

	private final VideoStreamProvider youtubeStreamProvider;

	@Autowired
	public YoutubeApiService(VideoStreamProvider youtubeStreamProvider) {
		this.youtubeStreamProvider = youtubeStreamProvider;
	}

	public VideoStreamContainer getYoutubeVideoStreamContainer(URL trackUrl) throws Exception {
		return youtubeStreamProvider.getVideoStreamContainer(trackUrl);
	}
}

package com.nabilanam.api.uselessapis.service.download;

import com.nabilanam.downloader.youtube.YoutubeClient;
import com.nabilanam.downloader.youtube.model.YoutubeStream;
import com.nabilanam.downloader.youtube.model.YoutubeStreamsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;
import java.util.List;

@Service
public class YoutubeApiService {

	private final YoutubeClient youtubeClient;

	@Autowired
	public YoutubeApiService(YoutubeClient youtubeClient) {
		this.youtubeClient = youtubeClient;
	}

	public YoutubeStreamsWrapper getDownloadLink(URL trackUrl) throws Exception {
		return youtubeClient.getDownloadLink(trackUrl);
	}
}

package com.nabilanam.api.uselessapis.service.download;

import com.nabilanam.downloader.twitter.TwitterClient;
import com.nabilanam.downloader.twitter.model.TwitterStreamsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class TwitterApiService {

	private final TwitterClient client;

	@Autowired
	public TwitterApiService(TwitterClient client) {
		this.client = client;
	}

	public TwitterStreamsWrapper getTwitterStreamsWrapper(URL url) throws Exception {
		return client.getTwitterStreamWrapper(url.toString());
	}
}

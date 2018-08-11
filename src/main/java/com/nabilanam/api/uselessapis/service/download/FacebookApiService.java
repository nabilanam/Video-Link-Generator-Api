package com.nabilanam.api.uselessapis.service.download;

import com.nabilanam.downloader.facebook.FacebookClient;
import com.nabilanam.downloader.facebook.model.FacebookStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class FacebookApiService {

	private final FacebookClient client;

	@Autowired
	public FacebookApiService(FacebookClient client) {
		this.client = client;
	}

	public List<FacebookStream> getDownloadLink(URL url) throws IOException {
		return client.getDownloadLink(url);
	}
}

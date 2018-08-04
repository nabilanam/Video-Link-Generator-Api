package com.nabilanam.api.uselessapis.service.DownloadLink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabilanam.api.uselessapis.model.ApiHost;
import com.nabilanam.api.uselessapis.model.DownloadLink.DownloadLink;
import com.nabilanam.api.uselessapis.model.DownloadLink.DownloadLinkManaged;
import com.nabilanam.api.uselessapis.model.DownloadLink.SoundCloudApi;
import com.nabilanam.api.uselessapis.repository.DownloadLink.DownloadLinkManagedRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class SoundCloudService {

	private final DownloadLinkManagedRepository repository;

	private ObjectMapper objectMapper;

	@Autowired
	public SoundCloudService(DownloadLinkManagedRepository repository) {
		this.objectMapper = new ObjectMapper();
		this.repository = repository;
	}

	DownloadLink getDownloadLink(URL trackUrl) throws IOException {
		DownloadLinkManaged linkAdmin = repository.findByApiHost(ApiHost.SOUNDCLOUD);
		URL apiUrl = linkAdmin.getApiUrl();
		String clientKey = linkAdmin.getClientSecret();
		String trackId = getTrackId(trackUrl);
		URL url = getUrl(apiUrl, clientKey, trackId);
		return new DownloadLink(url);
	}

	private URL getUrl(URL apiUrl, String clientKey, String trackId) throws IOException {
		String urlString = apiUrl.toString();
		urlString = urlString.replaceFirst("PLACE_HOLDER", trackId);
		urlString = urlString.replaceFirst("PLACE_HOLDER", clientKey);
		URL url = new URL(urlString);

		SoundCloudApi soundCloudApi = objectMapper.readValue(url, SoundCloudApi.class);
		return soundCloudApi.getHttp_mp3_128_url();
	}

	private String getTrackId(URL url) throws IOException {
		Document document = Jsoup.connect(url.toString()).get();
		Elements metas = document.select("meta[property=twitter:app:url:googleplay]");
		String content = metas.first().attr("content");
		return content.split(":")[2];
	}
}

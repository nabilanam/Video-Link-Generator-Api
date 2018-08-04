package com.nabilanam.api.uselessapis.service.DownloadLink;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabilanam.api.uselessapis.model.ApiHost;
import com.nabilanam.api.uselessapis.model.DownloadLink.DownloadLink;
import com.nabilanam.api.uselessapis.model.DownloadLink.DownloadLinkManaged;
import com.nabilanam.api.uselessapis.model.DownloadLink.SoundCloud.SoundCloudPlaylist;
import com.nabilanam.api.uselessapis.model.DownloadLink.SoundCloud.SoundCloudStream;
import com.nabilanam.api.uselessapis.model.DownloadLink.SoundCloud.SoundCloudTrack;
import com.nabilanam.api.uselessapis.repository.DownloadLink.DownloadLinkManagedRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Service
public class SoundCloudService {

	private final DownloadLinkManagedRepository repository;

	private final ObjectMapper objectMapper;

	@Autowired
	public SoundCloudService(DownloadLinkManagedRepository repository, ObjectMapper objectMapper) {
		this.repository = repository;
		this.objectMapper = objectMapper;
	}

	DownloadLink getDownloadLink(URL trackUrl) throws IOException {
		DownloadLinkManaged linkManaged = repository.findByApiHost(ApiHost.SOUNDCLOUD);
		URL apiUrl = linkManaged.getApiUrl();
		String clientKey = linkManaged.getClientSecret();
		String[] idTitle = getIdAndTitleFromWeb(trackUrl);
		String id = idTitle[0];
		String title = idTitle[1];
		URL url = getCustomizedUrl(apiUrl, clientKey, id);
		SoundCloudStream soundCloudStream = objectMapper.readValue(url, SoundCloudStream.class);
		return new DownloadLink(title, soundCloudStream.getHttp_mp3_128_url());
	}

	List<DownloadLink> getDownloadLinks(URL playListUrl) throws IOException {
		DownloadLinkManaged linkManaged = repository.findByApiHost(ApiHost.SOUNDCLOUD);
		URL playlistApiUrl = linkManaged.getPlaylistApiUrl();
		String clientKey = linkManaged.getClientSecret();
		String playListId = getIdFromWeb(playListUrl);
		URL url = getCustomizedUrl(playlistApiUrl, clientKey, playListId);
		SoundCloudPlaylist soundCloudPlaylist = objectMapper.readValue(url, SoundCloudPlaylist.class);
		List<SoundCloudTrack> tracks = soundCloudPlaylist.getTracks();
		List<DownloadLink> downloadLinks = new ArrayList<>();
		if (tracks != null) {
			DownloadLink downloadLink;
			for (SoundCloudTrack track : tracks) {
				if (track != null) {
					downloadLink = getDownloadLink(linkManaged, track.getTitle(), track.getId());
					if (downloadLink != null)
						downloadLinks.add(downloadLink);
				}
			}
		}
		return downloadLinks;
	}

	private DownloadLink getDownloadLink(DownloadLinkManaged linkManaged, String title, long trackId) throws MalformedURLException {
		URL apiUrl = linkManaged.getApiUrl();
		String clientKey = linkManaged.getClientSecret();
		URL url = getCustomizedUrl(apiUrl, clientKey, "" + trackId);
		SoundCloudStream soundCloudStream = null;
		try {
			soundCloudStream = objectMapper.readValue(url, SoundCloudStream.class);
		} catch (IOException e) {
			return null;
		}
		return new DownloadLink(title, soundCloudStream.getHttp_mp3_128_url());
	}

	private URL getCustomizedUrl(URL apiUrl, String clientKey, String id) throws MalformedURLException {
		String urlString = apiUrl.toString();
		urlString = urlString.replaceFirst("PLACE_HOLDER", id);
		urlString = urlString.replaceFirst("PLACE_HOLDER", clientKey);
		return new URL(urlString);
	}

	private String getIdFromWeb(URL url) throws IOException {
		Document document = Jsoup.connect(url.toString()).get();
		Elements metas = document.select("meta[property=twitter:app:url:googleplay]");
		String content = metas.first().attr("content");
		return content.split(":")[2];
	}

	private String[] getIdAndTitleFromWeb(URL url) throws IOException {
		Document document = Jsoup.connect(url.toString()).get();

		Elements metas = document.select("meta[property=twitter:title]");
		String content = metas.first().attr("content");
		String title = content;

		metas = document.select("meta[property=twitter:app:url:googleplay]");
		content = metas.first().attr("content");
		String id = content.split(":")[2];

		return new String[]{id, title};
	}
}

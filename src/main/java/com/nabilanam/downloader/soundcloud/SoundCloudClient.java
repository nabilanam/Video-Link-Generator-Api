package com.nabilanam.downloader.soundcloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabilanam.api.uselessapis.model.downloadlink.DownloadLink;
import com.nabilanam.downloader.soundcloud.model.SoundCloudPlaylist;
import com.nabilanam.downloader.soundcloud.model.SoundCloudStream;
import com.nabilanam.downloader.soundcloud.model.SoundCloudTrack;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SoundCloud {

	private final String clientKey = "OgPiZDOMYXnoiMl3ugzdAB5jGBGtroyf";
	private final String apiUrl = "https://api.soundcloud.com/i1/tracks/PLACE_HOLDER/streams?client_id=PLACE_HOLDER";
	private final String playlistApiUrl = "http://api.soundcloud.com/playlists/PLACE_HOLDER?client_id=PLACE_HOLDER";
	private final ObjectMapper objectMapper;

	@Autowired
	public SoundCloud(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	DownloadLink getDownloadLink(URL trackUrl) throws IOException {
		String[] idTitle = getIdAndTitleFromWeb(trackUrl);
		String id = idTitle[0];
		String title = idTitle[1];
		URL url = getCustomizedUrl(id);
		SoundCloudStream soundCloudStream = objectMapper.readValue(url, SoundCloudStream.class);
		return new DownloadLink(title, soundCloudStream.getHttp_mp3_128_url());
	}

	List<DownloadLink> getDownloadLinks(URL playListUrl) throws IOException {
		String id = getIdFromWeb(playListUrl);
		URL url = getCustomizedPlaylistUrl(id);
		SoundCloudPlaylist soundCloudPlaylist = objectMapper.readValue(url, SoundCloudPlaylist.class);
		List<SoundCloudTrack> tracks = soundCloudPlaylist.getTracks();
		List<DownloadLink> downloadLinks = new ArrayList<>();
		if (tracks != null) {
			DownloadLink downloadLink;
			for (SoundCloudTrack track : tracks) {
				if (track != null) {
					downloadLink = getDownloadLink(track.getTitle(), track.getId());
					if (downloadLink != null)
						downloadLinks.add(downloadLink);
				}
			}
		}
		return downloadLinks;
	}

	private DownloadLink getDownloadLink(String title, long trackId) throws MalformedURLException {
		URL url = getCustomizedUrl("" + trackId);
		SoundCloudStream soundCloudStream = null;
		try {
			soundCloudStream = objectMapper.readValue(url, SoundCloudStream.class);
		} catch (IOException e) {
			return null;
		}
		return new DownloadLink(title, soundCloudStream.getHttp_mp3_128_url());
	}

	private URL getCustomizedUrl(String id) throws MalformedURLException {
		String urlString = apiUrl;
		urlString = urlString.replaceFirst("PLACE_HOLDER", id);
		urlString = urlString.replaceFirst("PLACE_HOLDER", clientKey);
		return new URL(urlString);
	}

	private URL getCustomizedPlaylistUrl(String id) throws MalformedURLException {
		String urlString = playlistApiUrl;
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

package com.nabilanam.downloader.soundcloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabilanam.downloader.soundcloud.model.*;
import com.nabilanam.downloader.util.RegexUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class SoundCloudClient {

	private final String clientKey = "PmqbpuYsHUQ7ZYrW6qUlPcdpVFETRzc0";
	private final ObjectMapper objectMapper;
	private final RegexUtil regexUtil;

	@Autowired
	public SoundCloudClient(ObjectMapper objectMapper, RegexUtil regexUtil) {
		this.objectMapper = objectMapper;
		this.regexUtil = regexUtil;
	}

	public SoundCloudStreamsWrapper getDownloadLinks(URL playListUrl) throws IOException {
		Document document = getJsoupDocument(playListUrl);
		String playlistId = getIdFromDocument(document);
		String title = getTitleFromDocument(document);
		String thumbnailUrl = getThumbnailUrlFromDocument(document);
		URL url = getCustomizedPlaylistApiUrl(playlistId);
		Playlist playlist = objectMapper.readValue(url, Playlist.class);
		List<Track> tracks = playlist.getTracks();
		List<SoundCloudStream> streams = new ArrayList<>();
		SoundCloudStream soundCloudStream;
		for (Track track : tracks) {
			if (track != null) {
				soundCloudStream = getSoundCloudStream(track.getTitle(), track.getArtwork_url(), track.getId());
				if (soundCloudStream != null)
					streams.add(soundCloudStream);
			}
		}
		return new SoundCloudStreamsWrapper(title, thumbnailUrl, streams);
	}

	public SoundCloudStream getSoundCloudStream(URL trackUrl) throws IOException {
		Document document = getJsoupDocument(trackUrl);
		String id = getIdFromDocument(document);
		String title = getTitleFromDocument(document);
		String thumbnailUrl = getThumbnailUrlFromDocument(document);
		URL url = getCustomizedStreamApiUrl(id);
		Stream stream = objectMapper.readValue(url, Stream.class);
		return new SoundCloudStream(title, thumbnailUrl, stream.getHttp_mp3_128_url().toString());
	}

	private SoundCloudStream getSoundCloudStream(String title, String thumbnailUrl, long trackId) throws MalformedURLException {
		URL url = getCustomizedStreamApiUrl("" + trackId);
		Stream stream;
		try {
			stream = objectMapper.readValue(url, Stream.class);
		} catch (IOException e) {
			return null;
		}
		return new SoundCloudStream(title, thumbnailUrl, stream.getHttp_mp3_128_url().toString());
	}

	private URL getCustomizedStreamApiUrl(String id) throws MalformedURLException {
		String urlString = "https://api.soundcloud.com/i1/tracks/PLACE_HOLDER/streams?client_id=PLACE_HOLDER";
		urlString = urlString.replaceFirst("PLACE_HOLDER", id);
		urlString = urlString.replaceFirst("PLACE_HOLDER", clientKey);
		return new URL(urlString);
	}

	private URL getCustomizedPlaylistApiUrl(String id) throws MalformedURLException {
		String urlString = "http://api.soundcloud.com/playlists/PLACE_HOLDER?client_id=PLACE_HOLDER";
		urlString = urlString.replaceFirst("PLACE_HOLDER", id);
		urlString = urlString.replaceFirst("PLACE_HOLDER", clientKey);
		return new URL(urlString);
	}

	private String getThumbnailUrlFromDocument(Document document) {
		String imgRegex = "img\\ssrc=\"(.+?)(?=\")";
		Optional<String> groupOne = regexUtil.getGroupOne(document.html(), imgRegex);
		return groupOne.orElse("");
	}

	private String getIdFromDocument(Document document) {
		Elements metas = document.select("meta[property=twitter:app:url:googleplay]");
		String content = metas.first().attr("content");
		return content.split(":")[2];
	}

	private String getTitleFromDocument(Document document) {
		Elements metas = document.select("meta[property=twitter:title]");
		return metas.first().attr("content");
	}

	private Document getJsoupDocument(URL url) throws IOException {
		return Jsoup.connect(url.toString()).get();
	}
}

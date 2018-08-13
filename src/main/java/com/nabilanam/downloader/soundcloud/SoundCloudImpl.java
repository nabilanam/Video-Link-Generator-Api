package com.nabilanam.downloader.soundcloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.downloader.shared.model.AudioStream;
import com.nabilanam.downloader.shared.model.AudioStreamContainer;
import com.nabilanam.downloader.shared.util.RegexUtil;
import com.nabilanam.downloader.soundcloud.model.Playlist;
import com.nabilanam.downloader.soundcloud.model.Stream;
import com.nabilanam.downloader.soundcloud.model.Track;
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
public class SoundCloudImpl implements SoundCloud {
	private final String clientKey = "PmqbpuYsHUQ7ZYrW6qUlPcdpVFETRzc0";
	private final ObjectMapper objectMapper;
	private final RegexUtil regexUtil;

	@Autowired
	public SoundCloudImpl(ObjectMapper objectMapper, RegexUtil regexUtil) {
		this.objectMapper = objectMapper;
		this.regexUtil = regexUtil;
	}

	public AudioStream getAudioStream(URL trackUrl) throws ResourceNotFoundException {
		AudioStream audioStream;
		try {
			Document document = getJsoupDocument(trackUrl);
			String id = getIdFromDocument(document);
			String title = getTitleFromDocument(document);
			String thumbnailUrl = getThumbnailUrlFromDocument(document);
			URL url = getCustomizedStreamApiUrl(id);
			Stream stream = objectMapper.readValue(url, Stream.class);
			audioStream = new AudioStream(title, thumbnailUrl, stream.getHttp_mp3_128_url().toString());
		} catch (Exception ex) {
			throw new ResourceNotFoundException();
		}
		return audioStream;
	}

	public AudioStreamContainer getAudioStreamContainer(URL playListUrl) throws ResourceNotFoundException {
		AudioStreamContainer container;
		try {
			Document document = getJsoupDocument(playListUrl);
			String playlistId = getIdFromDocument(document);
			String title = getTitleFromDocument(document);
			String thumbnailUrl = getThumbnailUrlFromDocument(document);
			URL url = getCustomizedPlaylistApiUrl(playlistId);
			Playlist playlist = objectMapper.readValue(url, Playlist.class);
			List<Track> tracks = playlist.getTracks();
			List<AudioStream> streams = new ArrayList<>();
			AudioStream audioStream;
			for (Track track : tracks) {
				if (track != null) {
					audioStream = getSoundCloudStream(track.getTitle(), track.getArtwork_url(), track.getId());
					if (audioStream != null)
						streams.add(audioStream);
				}
			}
			container = new AudioStreamContainer(title, thumbnailUrl, streams);
		} catch (Exception ex) {
			throw new ResourceNotFoundException();
		}
		return container;
	}

	private AudioStream getSoundCloudStream(String title, String thumbnailUrl, long trackId) throws MalformedURLException {
		URL url = getCustomizedStreamApiUrl("" + trackId);
		Stream stream;
		try {
			stream = objectMapper.readValue(url, Stream.class);
		} catch (IOException e) {
			return null;
		}
		return new AudioStream(title, thumbnailUrl, stream.getHttp_mp3_128_url().toString());
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

package com.nabilanam.downloader.soundcloud;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabilanam.downloader.soundcloud.model.Playlist;
import com.nabilanam.downloader.soundcloud.model.SoundCloudSingle;
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

@Component
public class SoundCloudClient {

	private final String clientKey = "OgPiZDOMYXnoiMl3ugzdAB5jGBGtroyf";
	private final ObjectMapper objectMapper;

	@Autowired
	public SoundCloudClient(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	public SoundCloudSingle getDownloadLink(URL trackUrl) throws IOException {
		Document document = getJsoupDocument(trackUrl);
		String id = getIdFromDocument(document);
		String title = getTrackTitleFromDocument(document);
		URL url = getCustomizedUrl(id);
		Stream stream = objectMapper.readValue(url, Stream.class);
		return new SoundCloudSingle(title, stream.getHttp_mp3_128_url());
	}

	public List<SoundCloudSingle> getDownloadLinks(URL playListUrl) throws IOException {
		Document document = getJsoupDocument(playListUrl);
		String playlistId = getIdFromDocument(document);
		URL url = getCustomizedPlaylistUrl(playlistId);
		Playlist playlist = objectMapper.readValue(url, Playlist.class);
		List<Track> tracks = playlist.getTracks();
		List<SoundCloudSingle> soundCloudSingles = new ArrayList<>();
		if (tracks != null) {
			SoundCloudSingle soundCloudSingle;
			for (Track track : tracks) {
				if (track != null) {
					soundCloudSingle = getDownloadLink(track.getTitle(), track.getId());
					if (soundCloudSingle != null)
						soundCloudSingles.add(soundCloudSingle);
				}
			}
		}
		return soundCloudSingles;
	}

	private SoundCloudSingle getDownloadLink(String title, long trackId) throws MalformedURLException {
		URL url = getCustomizedUrl("" + trackId);
		Stream stream;
		try {
			stream = objectMapper.readValue(url, Stream.class);
		} catch (IOException e) {
			return null;
		}
		return new SoundCloudSingle(title, stream.getHttp_mp3_128_url());
	}

	private URL getCustomizedUrl(String id) throws MalformedURLException {
		String urlString = "https://api.soundcloud.com/i1/tracks/PLACE_HOLDER/streams?client_id=PLACE_HOLDER";
		urlString = urlString.replaceFirst("PLACE_HOLDER", id);
		urlString = urlString.replaceFirst("PLACE_HOLDER", clientKey);
		return new URL(urlString);
	}

	private URL getCustomizedPlaylistUrl(String id) throws MalformedURLException {
		String urlString = "http://api.soundcloud.com/playlists/PLACE_HOLDER?client_id=PLACE_HOLDER";
		urlString = urlString.replaceFirst("PLACE_HOLDER", id);
		urlString = urlString.replaceFirst("PLACE_HOLDER", clientKey);
		return new URL(urlString);
	}

	private String getIdFromDocument(Document document) {
		Elements metas = document.select("meta[property=twitter:app:url:googleplay]");
		String content = metas.first().attr("content");
		return content.split(":")[2];
	}

	private String getTrackTitleFromDocument(Document document) {
		Elements metas = document.select("meta[property=twitter:title]");
		return metas.first().attr("content");
	}

	private Document getJsoupDocument(URL url) throws IOException {
		return Jsoup.connect(url.toString()).get();
	}
}

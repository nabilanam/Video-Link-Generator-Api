package com.nabilanam.api.uselessapis.service.download;

import com.nabilanam.downloader.soundcloud.SoundCloudClient;
import com.nabilanam.downloader.soundcloud.model.SoundCloudStream;
import com.nabilanam.downloader.soundcloud.model.SoundCloudStreamsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class SoundCloudApiService {

	private final SoundCloudClient soundCloudClient;

	@Autowired
	public SoundCloudApiService(SoundCloudClient soundCloudClient) {
		this.soundCloudClient = soundCloudClient;
	}

	public SoundCloudStream getDownloadLink(URL trackUrl) throws IOException {
		return soundCloudClient.getSoundCloudStream(trackUrl);
	}

	public SoundCloudStreamsWrapper getPlaylistDownloadLinks(URL playlistUrl) throws IOException {
		return soundCloudClient.getDownloadLinks(playlistUrl);
	}
}

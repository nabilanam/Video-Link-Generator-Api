package com.nabilanam.api.uselessapis.service.downloadlink;

import com.nabilanam.downloader.soundcloud.SoundCloudClient;
import com.nabilanam.downloader.soundcloud.model.SoundCloudSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class SoundCloudApiService {

	private final SoundCloudClient soundCloudClient;

	@Autowired
	public SoundCloudApiService(SoundCloudClient soundCloudClient) {
		this.soundCloudClient = soundCloudClient;
	}

	public SoundCloudSingle getDownloadLink(URL trackUrl) throws IOException {
		return soundCloudClient.getDownloadLink(trackUrl);
	}

	public List<SoundCloudSingle> getPlaylistDownloadLinks(URL playlistUrl) throws IOException {
		return soundCloudClient.getDownloadLinks(playlistUrl);
	}
}

package com.nabilanam.api.uselessapis.service.downloadlink;

import com.nabilanam.downloader.soundcloud.SoundCloudClient;
import com.nabilanam.downloader.soundcloud.model.SoundCloudSingle;
import com.nabilanam.downloader.youtube.YoutubeClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@Service
public class DownloadLinkApiService {

	private final SoundCloudClient soundCloudClient;
	private final YoutubeClient youtubeClient;

	@Autowired
	public DownloadLinkApiService(SoundCloudClient soundCloudClient, YoutubeClient youtubeClient) {
		this.soundCloudClient = soundCloudClient;
		this.youtubeClient = youtubeClient;
	}

	public SoundCloudSingle getSoundCloudDownloadLink(URL trackUrl) throws IOException {
		return soundCloudClient.getDownloadLink(trackUrl);
	}

	public List<SoundCloudSingle> getSoundCloudPlaylistDownloadLinks(URL playlistUrl) throws IOException {
		return soundCloudClient.getDownloadLinks(playlistUrl);
	}
}

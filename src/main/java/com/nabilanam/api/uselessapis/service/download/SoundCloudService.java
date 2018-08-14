package com.nabilanam.api.uselessapis.service.download;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.downloader.shared.model.AudioStream;
import com.nabilanam.downloader.shared.model.AudioStreamContainer;
import com.nabilanam.downloader.soundcloud.SoundCloud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public class SoundCloudService {

	private final SoundCloud soundCloud;

	@Autowired
	public SoundCloudService(SoundCloud soundCloud) {
		this.soundCloud = soundCloud;
	}

	public AudioStream getSoundCloudAudioStream(URL trackUrl) throws ResourceNotFoundException {
		return soundCloud.getAudioStream(trackUrl);
	}

	public AudioStreamContainer getSoundCloudAudioStreamContainer(URL playlistUrl) throws ResourceNotFoundException {
		return soundCloud.getAudioStreamContainer(playlistUrl);
	}
}

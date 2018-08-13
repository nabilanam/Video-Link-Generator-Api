package com.nabilanam.downloader.soundcloud;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.downloader.shared.model.AudioStream;
import com.nabilanam.downloader.shared.model.AudioStreamContainer;

import java.net.URL;

public interface SoundCloud {

	public AudioStream getAudioStream(URL url) throws ResourceNotFoundException;

	public AudioStreamContainer getAudioStreamContainer(URL url) throws ResourceNotFoundException;
}

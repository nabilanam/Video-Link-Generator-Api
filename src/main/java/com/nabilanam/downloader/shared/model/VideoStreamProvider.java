package com.nabilanam.downloader.shared.model;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;

import java.net.URL;

public interface VideoStreamProvider {

	public VideoStreamContainer getVideoStreamContainer(URL url) throws ResourceNotFoundException;
}

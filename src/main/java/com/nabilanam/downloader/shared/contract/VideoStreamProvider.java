package com.nabilanam.downloader.shared.contract;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;

import java.net.URL;

public interface VideoStreamProvider {

	public VideoStreamContainer getVideoStreamContainer(URL url) throws ResourceNotFoundException;
}

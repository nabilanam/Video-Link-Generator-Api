package com.nabilanam.api.uselessapis.service.download.contract;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import org.springframework.stereotype.Service;

import java.net.URL;

@Service
public interface VideoService {

	public VideoStreamContainer getVideoStreamContainer(URL url) throws ResourceNotFoundException;
}

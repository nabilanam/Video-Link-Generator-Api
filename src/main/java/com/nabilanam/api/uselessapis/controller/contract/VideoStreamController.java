package com.nabilanam.api.uselessapis.controller.contract;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.api.uselessapis.request.SimpleDownloadRequest;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;

import java.io.IOException;

public interface VideoStreamController {

	public VideoStreamContainer getVideoStreamContainer(SimpleDownloadRequest request) throws IOException, ResourceNotFoundException;
}

package com.nabilanam.api.uselessapis.controller;

import com.nabilanam.api.uselessapis.controller.contract.VideoStreamController;
import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.api.uselessapis.request.SimpleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.contract.VideoService;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;

@CrossOrigin
@RestController
@RequestMapping("/service/download/youtube/")
@Api(tags = "Youtube", description = "Public video")
public class YoutubeController implements VideoStreamController {

	private final VideoService youtubeService;

	@Autowired
	public YoutubeController(VideoService youtubeService) {
		this.youtubeService = youtubeService;
	}

	@Override
	@PostMapping
			(
					value = "single/",
					headers = {"content-type=application/json"},
					produces = MediaType.APPLICATION_JSON_VALUE
			)
	@ApiOperation("Provides download information for a public video.")
	public VideoStreamContainer getVideoStreamContainer(@RequestBody SimpleDownloadRequest request) throws IOException, ResourceNotFoundException {
		return youtubeService.getVideoStreamContainer(new URL(request.getUrl()));
	}
}

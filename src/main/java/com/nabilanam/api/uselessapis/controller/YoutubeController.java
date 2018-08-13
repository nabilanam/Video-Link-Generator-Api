package com.nabilanam.api.uselessapis.controller;

import com.nabilanam.api.uselessapis.request.SimpleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.YoutubeApiService;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

@CrossOrigin
@RestController
@RequestMapping("/service/download/youtube/")
@Api(tags = "Youtube", description = "Public video")
public class YoutubeController {

	private final YoutubeApiService service;

	@Autowired
	public YoutubeController(YoutubeApiService service) {
		this.service = service;
	}

	@ApiOperation("Provides download information for a public video.")
	@PostMapping
			(
					value = "single/",
					headers = {"content-type=application/json"},
					produces = MediaType.APPLICATION_JSON_VALUE
			)
	public VideoStreamContainer getVideoStreamContainer(@RequestBody SimpleDownloadRequest request) throws Exception {
		return service.getYoutubeVideoStreamContainer(new URL(request.getUrl()));
	}
}

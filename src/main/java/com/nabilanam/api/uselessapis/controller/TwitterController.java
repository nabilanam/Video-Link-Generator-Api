package com.nabilanam.api.uselessapis.controller;

import com.nabilanam.api.uselessapis.request.SimpleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.TwitterApiService;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

@CrossOrigin
@RestController
@RequestMapping("/service/download/twitter/")
@Api(tags = "Twitter", description = "Public video")
public class TwitterController {

	private final TwitterApiService service;

	@Autowired
	public TwitterController(TwitterApiService service) {
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
		return service.getTwitterVideoStreamContainer(new URL(request.getUrl()));
	}
}

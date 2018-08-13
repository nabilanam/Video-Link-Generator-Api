package com.nabilanam.api.uselessapis.controller;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.api.uselessapis.request.SimpleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.FacebookApiService;
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
@RequestMapping("/service/download/facebook/")
@Api(tags = "Facebook", description = "Public video")
public class FacebookController {

	private final FacebookApiService service;

	@Autowired
	public FacebookController(FacebookApiService service) {
		this.service = service;
	}

	@ApiOperation("Provides download information for a public video.")
	@PostMapping
			(
					value = "single/",
					headers = {"content-type=application/json"},
					produces = MediaType.APPLICATION_JSON_VALUE
			)
	public VideoStreamContainer getVideoStreamContainer(@RequestBody SimpleDownloadRequest request) throws IOException, ResourceNotFoundException {
		return service.getFacebookVideoStreamContainer(new URL(request.getUrl()));
	}
}

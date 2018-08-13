package com.nabilanam.api.uselessapis.controller;

import com.nabilanam.api.uselessapis.request.SingleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.FacebookApiService;
import com.nabilanam.downloader.facebook.model.FacebookStream;
import com.nabilanam.downloader.facebook.model.FacebookStreamsWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/service/download/facebook/")
@Api(tags ="Facebook", description = "Video download")
public class FacebookRequestController {

	private final FacebookApiService service;

	@Autowired
	public FacebookRequestController(FacebookApiService service) {
		this.service = service;
	}

	@ApiOperation("provides download information for a public video")
	@PostMapping
			(
					value = "single/",
					headers = {"content-type=application/json"},
					produces= MediaType.APPLICATION_JSON_VALUE
			)
	public FacebookStreamsWrapper getDownloadLink(@RequestBody SingleDownloadRequest request) throws IOException {
		return service.getDownloadLink(new URL(request.getUrl()));
	}
}

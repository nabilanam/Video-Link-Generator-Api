package com.nabilanam.api.uselessapis.controller.download;

import com.nabilanam.api.uselessapis.request.download.SingleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.TwitterApiService;
import com.nabilanam.downloader.twitter.model.TwitterStreamsWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

@CrossOrigin
@RestController
@RequestMapping("/service/download/twitter/")
@Api(tags ="Twitter", description = "Video download")
public class TwitterRequestController {

	private final TwitterApiService service;

	@Autowired
	public TwitterRequestController(TwitterApiService service) {
		this.service = service;
	}

	@ApiOperation("provides download url for a public video")
	@PostMapping
			(
					value = "single/",
					headers = {"content-type=application/json"},
					produces= MediaType.APPLICATION_JSON_VALUE
			)
	public TwitterStreamsWrapper getDownloadLink(@RequestBody SingleDownloadRequest request) throws Exception {
		return service.getTwitterStreamsWrapper(new URL(request.getUrl()));
	}
}

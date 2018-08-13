package com.nabilanam.api.uselessapis.controller;

import com.nabilanam.api.uselessapis.request.SingleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.YoutubeApiService;
import com.nabilanam.downloader.youtube.model.YoutubeStreamsWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URL;

@CrossOrigin
@RestController
@RequestMapping("/service/download/youtube/")
@Api(tags ="Youtube", description = "video download")
public class YoutubeRequestController {

	private final YoutubeApiService service;

	@Autowired
	public YoutubeRequestController(YoutubeApiService service) {
		this.service = service;
	}

	@ApiOperation("provides download information for a public video")
	@PostMapping
			(
					value = "single/",
					headers = {"content-type=application/json"},
					produces= MediaType.APPLICATION_JSON_VALUE
			)
	public YoutubeStreamsWrapper getDownloadLink(@RequestBody SingleDownloadRequest request) throws Exception {
		return service.getDownloadLink(new URL(request.getUrl()));
	}
}

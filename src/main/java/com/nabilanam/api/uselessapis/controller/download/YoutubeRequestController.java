package com.nabilanam.api.uselessapis.controller.download;

import com.nabilanam.api.uselessapis.request.download.SingleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.YoutubeApiService;
import com.nabilanam.downloader.youtube.model.YoutubeStream;
import com.nabilanam.downloader.youtube.model.YoutubeStreamsWrapper;
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
@RequestMapping("/service/download/youtube/")
@Api(tags ="Youtube", description = "video download")
public class YoutubeRequestController {

	private final YoutubeApiService service;

	@Autowired
	public YoutubeRequestController(YoutubeApiService service) {
		this.service = service;
	}

	@ApiOperation("provides download url for a public video")
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

package com.nabilanam.api.uselessapis.controller.download;

import com.nabilanam.api.uselessapis.request.download.SingleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.TwitterApiService;
import com.nabilanam.downloader.twitter.model.TwitterStreamsWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/service/download/twitter/")
public class TwitterRequestController {

	private final TwitterApiService service;

	@Autowired
	public TwitterRequestController(TwitterApiService service) {
		this.service = service;
	}

	@PostMapping
			(
					value = "single/",
					headers = {"content-type=application/json"}
			)
	public TwitterStreamsWrapper getDownloadLink(@RequestBody SingleDownloadRequest request) throws Exception {
		return service.getTwitterStreamsWrapper(request.getUrl().toString());
	}
}

package com.nabilanam.api.uselessapis.controller.download;

import com.nabilanam.api.uselessapis.request.download.SingleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.FacebookApiService;
import com.nabilanam.downloader.facebook.model.FacebookStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/service/download/facebook/")
public class FacebookRequestController {

	private final FacebookApiService service;

	@Autowired
	public FacebookRequestController(FacebookApiService service) {
		this.service = service;
	}

	@PostMapping("single/")
	public List<FacebookStream> getDownloadLink(@RequestBody SingleDownloadRequest request) throws IOException {
		return service.getDownloadLink(request.getUrl());
	}
}

package com.nabilanam.api.uselessapis.controller.download;

import com.nabilanam.api.uselessapis.request.download.SingleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.YoutubeApiService;
import com.nabilanam.downloader.youtube.model.YoutubeStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/service/download/youtube/")
public class YoutubeRequestController {

	private final YoutubeApiService service;

	@Autowired
	public YoutubeRequestController(YoutubeApiService service) {
		this.service = service;
	}

	@PostMapping
			(
					value = "single/",
					headers = {"content-type=application/json"}
			)
	public List<YoutubeStream> getDownloadLink(@RequestBody SingleDownloadRequest request) throws Exception {
		return service.getDownloadLink(request.getUrl());
	}

	@PostMapping
			(
					value = "playlist/",
					headers = {"content-type=application/json"}
			)
	public List<YoutubeStream> getDownloadLinks(@RequestBody SingleDownloadRequest request) throws IOException {
		return null;
	}
}

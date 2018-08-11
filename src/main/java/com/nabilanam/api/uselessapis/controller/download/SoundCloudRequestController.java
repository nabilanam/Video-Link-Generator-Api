package com.nabilanam.api.uselessapis.controller.download;

import com.nabilanam.api.uselessapis.request.download.SingleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.SoundCloudApiService;
import com.nabilanam.downloader.soundcloud.model.SoundCloudStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/service/download/soundcloud/")
public class SoundCloudRequestController {

	private final SoundCloudApiService service;

	@Autowired
	public SoundCloudRequestController(SoundCloudApiService service) {
		this.service = service;
	}

	@PostMapping
			(
					value = "single/",
					headers = {"content-type=application/json"}
			)
	public SoundCloudStream getDownloadLink(@RequestBody SingleDownloadRequest request) throws IOException {
		return service.getDownloadLink(request.getUrl());
	}

	@PostMapping
			(
					value = "playlist/",
					headers = {"content-type=application/json"}
			)
	public List<SoundCloudStream> getDownloadLinks(@RequestBody SingleDownloadRequest request) throws IOException {
		return service.getPlaylistDownloadLinks(request.getUrl());
	}
}

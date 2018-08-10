package com.nabilanam.api.uselessapis.controller.downloadlink;

import com.nabilanam.api.uselessapis.request.downloadlink.DownloadLinkRequest;
import com.nabilanam.api.uselessapis.service.downloadlink.SoundCloudApiService;
import com.nabilanam.downloader.soundcloud.model.SoundCloudSingle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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
	public SoundCloudSingle getDownloadLink(@RequestBody DownloadLinkRequest request) throws IOException {
		return service.getDownloadLink(request.getUrl());
	}

	@PostMapping
			(
					value = "playlist/",
					headers = {"content-type=application/json"}
			)
	public List<SoundCloudSingle> getDownloadLinks(@RequestBody DownloadLinkRequest request) throws IOException {
		return service.getPlaylistDownloadLinks(request.getUrl());
	}
}

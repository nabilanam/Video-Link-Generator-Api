package com.nabilanam.api.uselessapis.controller;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.api.uselessapis.request.SimpleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.SoundCloudApiService;
import com.nabilanam.downloader.shared.model.AudioStream;
import com.nabilanam.downloader.shared.model.AudioStreamContainer;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;
import java.net.URL;

@CrossOrigin
@RestController
@RequestMapping("/service/download/soundcloud/")
@Api(tags = "SoundCloud", description = "Audio track, playlist")
public class SoundCloudController {

	private final SoundCloudApiService service;

	@Autowired
	public SoundCloudController(SoundCloudApiService service) {
		this.service = service;
	}

	@ApiOperation("Provides download information for a single track.")
	@PostMapping
			(
					value = "single/",
					headers = {"content-type=application/json"},
					produces = MediaType.APPLICATION_JSON_VALUE
			)
	public AudioStream getAudioStream(@RequestBody SimpleDownloadRequest request) throws MalformedURLException, ResourceNotFoundException {
		return service.getSoundCloudAudioStream(new URL(request.getUrl()));
	}

	@ApiOperation("Provides download information for a playlist. Large playlist can occur timeout.")
	@PostMapping
			(
					value = "playlist/",
					headers = {"content-type=application/json"},
					produces = MediaType.APPLICATION_JSON_VALUE
			)
	public AudioStreamContainer getAudioStreamContainer(@RequestBody SimpleDownloadRequest request) throws MalformedURLException, ResourceNotFoundException {
		return service.getSoundCloudAudioStreamContainer(new URL(request.getUrl()));
	}
}

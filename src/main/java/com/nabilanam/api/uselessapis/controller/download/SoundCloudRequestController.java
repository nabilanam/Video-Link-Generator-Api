package com.nabilanam.api.uselessapis.controller.download;

import com.nabilanam.api.uselessapis.request.download.SingleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.SoundCloudApiService;
import com.nabilanam.downloader.soundcloud.model.SoundCloudStream;
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
@RequestMapping("/service/download/soundcloud/")
@Api(tags ="SoundCloud", description = "Audio download")
public class SoundCloudRequestController {

	private final SoundCloudApiService service;

	@Autowired
	public SoundCloudRequestController(SoundCloudApiService service) {
		this.service = service;
	}

	@ApiOperation("provides download url for a single track")
	@PostMapping
			(
					value = "single/",
					headers = {"content-type=application/json"},
					produces= MediaType.APPLICATION_JSON_VALUE
			)
	public SoundCloudStream getDownloadLink(@RequestBody SingleDownloadRequest request) throws IOException {
		return service.getDownloadLink(new URL(request.getUrl()));
	}

	@ApiOperation("provides download url for a playlist")
	@PostMapping
			(
					value = "playlist/",
					headers = {"content-type=application/json"},
					produces= MediaType.APPLICATION_JSON_VALUE
			)
	public List<SoundCloudStream> getDownloadLinks(@RequestBody SingleDownloadRequest request) throws IOException {
		return service.getPlaylistDownloadLinks(new URL(request.getUrl()));
	}
}

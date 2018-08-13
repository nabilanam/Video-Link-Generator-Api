package com.nabilanam.api.uselessapis.controller.download;

import com.nabilanam.api.uselessapis.request.download.SingleDownloadRequest;
import com.nabilanam.api.uselessapis.service.download.FacebookApiService;
import com.nabilanam.downloader.facebook.model.FacebookStream;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URL;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/service/download/facebook/")
@Api(tags ="Facebook", description = "Video download")
public class FacebookRequestController {

	private final FacebookApiService service;

	@Autowired
	public FacebookRequestController(FacebookApiService service) {
		this.service = service;
	}

	@ApiOperation("provides download url for a public video")
	@PostMapping
			(
					value = "single/",
					headers = {"content-type=application/json"},
					produces= MediaType.APPLICATION_JSON_VALUE
			)
	public List<FacebookStream> getDownloadLink(@RequestBody SingleDownloadRequest request) throws IOException {
		return service.getDownloadLink(new URL(request.getUrl()));
	}
}

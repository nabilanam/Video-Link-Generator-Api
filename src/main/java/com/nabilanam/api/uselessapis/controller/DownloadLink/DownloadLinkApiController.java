package com.nabilanam.api.uselessapis.controller.DownloadLink;

import com.nabilanam.api.uselessapis.model.DownloadLink.DownloadLink;
import com.nabilanam.api.uselessapis.request.DownloadLink.DownloadLinkApiRequest;
import com.nabilanam.api.uselessapis.service.DownloadLink.DownloadLinkApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/service/downloadlink")
public class DownloadLinkApiController {

	private final DownloadLinkApiService service;

	@Autowired
	public DownloadLinkApiController(DownloadLinkApiService service) {
		this.service = service;
	}

	@PostMapping
			(
					value = "/",
					headers = {"content-type=application/json"}
			)
	public DownloadLink getDownloadLink(@RequestBody DownloadLinkApiRequest request) throws IOException {
		return service.getDownloadLink(request.getApiHost(), request.getTrackUrl());
	}
}

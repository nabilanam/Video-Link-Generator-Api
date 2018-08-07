package com.nabilanam.api.uselessapis.controller.DownloadLink;

import com.nabilanam.api.uselessapis.model.DownloadLink.DownloadLink;
import com.nabilanam.api.uselessapis.request.DownloadLink.DownloadLinkApiRequest;
import com.nabilanam.api.uselessapis.service.DownloadLink.DownloadLinkApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
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
		return service.getDownloadLink(request.getApiHost(), request.getUrl());
	}

	@PostMapping
			(
					value = "/playlist/",
					headers = {"content-type=application/json"}
			)
	public List<DownloadLink> getDownloadLinks(@RequestBody DownloadLinkApiRequest request) throws IOException {
		return service.getDownloadLinks(request.getApiHost(), request.getUrl());
	}
}

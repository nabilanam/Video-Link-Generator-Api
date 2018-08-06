package com.nabilanam.api.uselessapis.controller.downloadlink;

import com.nabilanam.api.uselessapis.model.downloadlink.DownloadLink;
import com.nabilanam.api.uselessapis.request.downloadlink.DownloadLinkApiRequest;
import com.nabilanam.api.uselessapis.service.downloadlink.DownloadLinkApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

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

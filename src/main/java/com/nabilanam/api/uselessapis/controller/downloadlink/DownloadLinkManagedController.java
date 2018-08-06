package com.nabilanam.api.uselessapis.controller.downloadlink;

import com.nabilanam.api.uselessapis.exception.ApiNotFoundException;
import com.nabilanam.api.uselessapis.model.ApiHost;
import com.nabilanam.api.uselessapis.model.downloadlink.DownloadLinkManaged;
import com.nabilanam.api.uselessapis.repository.DownloadLink.DownloadLinkManagedRepository;
import com.nabilanam.api.uselessapis.request.downloadlink.DownloadLinkManagedRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.net.URL;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/managed/downloadlink")
public class DownloadLinkManagedController {

	private final DownloadLinkManagedRepository downloadLinkManagedRepository;

	@Autowired
	public DownloadLinkManagedController(DownloadLinkManagedRepository downloadLinkManagedRepository) {
		this.downloadLinkManagedRepository = downloadLinkManagedRepository;
	}

	@GetMapping("/")
	@ResponseStatus(HttpStatus.OK)
	public List<DownloadLinkManaged> getAllDownloadLinkApi() {
		return downloadLinkManagedRepository.findAll();
	}

	@PostMapping
			(
					value = "/",
					headers = {"content-type=application/json"},
					consumes = MediaType.APPLICATION_JSON_VALUE
			)
	@ResponseStatus(HttpStatus.CREATED)
	public DownloadLinkManaged createDownloaderApi(@RequestBody DownloadLinkManagedRequest managedRequest) {
		ApiHost apiHost = managedRequest.getApiHost();
		URL apiUrl = managedRequest.getApiUrl();
		URL playlistApiUrl = managedRequest.getPlaylistApiUrl();
		String secret = managedRequest.getClientSecret();
		DownloadLinkManaged linkManaged = new DownloadLinkManaged(apiHost, apiUrl, playlistApiUrl, secret);
		return downloadLinkManagedRepository.save(linkManaged);
	}

	@GetMapping
			(
					value = "/{id}/",
					headers = {"content-type=application/json"}
			)
	@ResponseStatus(HttpStatus.OK)
	public DownloadLinkManaged getDownloadLinkApi(@PathVariable int id) {
		Optional<DownloadLinkManaged> linkManagedOptional = getManagedLinkOptional(id);
		if (linkManagedOptional.isPresent()) {
			return linkManagedOptional.get();
		} else throw new ApiNotFoundException("Api not found for id " + id);
	}

	@PutMapping
			(
					value = "/{id}/",
					headers = {"content-type=application/json"},
					consumes = MediaType.APPLICATION_JSON_VALUE
			)
	@ResponseStatus(HttpStatus.OK)
	public DownloadLinkManaged updateDownloaderApi(@PathVariable int id, @RequestBody DownloadLinkManagedRequest managedRequest) {
		Optional<DownloadLinkManaged> apiOptional = getManagedLinkOptional(id);
		if (apiOptional.isPresent()) {
			DownloadLinkManaged linkManaged = apiOptional.get();
			linkManaged.setApiHost(managedRequest.getApiHost());
			linkManaged.setApiUrl(managedRequest.getApiUrl());
			return downloadLinkManagedRepository.save(linkManaged);
		} else throw new ApiNotFoundException("Api not found for id " + id);
	}

	@DeleteMapping
			(
					value = "/{id}/",
					headers = {"content-type=application/json"}
			)
	@ResponseStatus(HttpStatus.OK)
	public DownloadLinkManaged deleteDownloaderApi(@PathVariable int id) {
		Optional<DownloadLinkManaged> apiOptional = getManagedLinkOptional(id);
		if (apiOptional.isPresent()) {
			DownloadLinkManaged linkManaged = apiOptional.get();
			downloadLinkManagedRepository.delete(linkManaged);
			return linkManaged;
		} else throw new ApiNotFoundException("Api not found for id " + id);
	}

	private Optional<DownloadLinkManaged> getManagedLinkOptional(int id) {
		return downloadLinkManagedRepository.findById(id);
	}
}

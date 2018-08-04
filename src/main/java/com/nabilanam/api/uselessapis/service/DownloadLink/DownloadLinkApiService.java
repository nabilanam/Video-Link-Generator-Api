package com.nabilanam.api.uselessapis.service.DownloadLink;

import com.nabilanam.api.uselessapis.exception.ApiNotFoundException;
import com.nabilanam.api.uselessapis.model.ApiHost;
import com.nabilanam.api.uselessapis.model.DownloadLink.DownloadLink;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class DownloadLinkApiService {

	private final SoundCloudService soundCloudService;

	@Autowired
	public DownloadLinkApiService(SoundCloudService soundCloudService) {
		this.soundCloudService = soundCloudService;
	}

	public DownloadLink getDownloadLink(ApiHost apiHost, URL trackUrl) throws IOException {
		if (apiHost == ApiHost.SOUNDCLOUD){
			return soundCloudService.getDownloadLink(trackUrl);
		} else throw new ApiNotFoundException("Api not found for "+apiHost.name());
	}
}

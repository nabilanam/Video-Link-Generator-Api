package com.nabilanam.api.uselessapis.model.DownloadLink.SoundCloud;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoundCloudUser {
	private long id;
}

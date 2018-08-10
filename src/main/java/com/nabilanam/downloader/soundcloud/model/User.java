package com.nabilanam.downloader.soundcloud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoundCloudUser {
	private long id;
}

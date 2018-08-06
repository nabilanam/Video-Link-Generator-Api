package com.nabilanam.api.uselessapis.model.downloadlink.soundcloud;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SoundCloudPlaylist {
	private List<SoundCloudTrack> tracks;
}

package com.nabilanam.downloader.soundcloud.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Playlist {
	private List<Track> tracks;
}

package com.nabilanam.downloader.twitter.model.twitterapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ExtendedEntities {

	private List<Media> media;
}

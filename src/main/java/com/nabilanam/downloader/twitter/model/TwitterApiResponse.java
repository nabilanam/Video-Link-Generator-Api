package com.nabilanam.downloader.twitter.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterApiResponse {

	private String text;
	private ExtendedEntities extended_entities;
}

package com.nabilanam.downloader.twitter.model.twitterapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URL;
import java.util.List;


@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TwitterApiResponse {

	private String text;
	private ExtendedEntities extended_entities;
}

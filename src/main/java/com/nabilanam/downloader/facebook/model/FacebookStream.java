package com.nabilanam.downloader.facebook.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.net.URL;

@Data
@AllArgsConstructor
public class FacebookStream {

	private String url;
	private VideoQuality videoQuality;
}

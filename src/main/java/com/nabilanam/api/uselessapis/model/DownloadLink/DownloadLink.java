package com.nabilanam.api.uselessapis.model.DownloadLink;

import lombok.Data;

import java.net.URL;

@Data
public class DownloadLink {

	private String title;
	private URL url;

	public DownloadLink(String title, URL url) {
		this.title = title;
		this.url = url;
	}
}

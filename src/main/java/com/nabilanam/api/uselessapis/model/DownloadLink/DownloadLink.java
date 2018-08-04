package com.nabilanam.api.uselessapis.model.DownloadLink;

import lombok.Data;

import java.net.URL;

@Data
public class DownloadLink {

	private URL url;

	public DownloadLink(URL url) {
		this.url = url;
	}
}

package com.nabilanam.api.uselessapis.request.download;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SingleDownloadRequest {
	private URL url;
}

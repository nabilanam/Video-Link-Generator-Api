package com.nabilanam.api.uselessapis.model.downloadlink;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.net.URL;

@Data
@AllArgsConstructor
public class SoundCloudSingle {

	private String title;
	private URL url;
}

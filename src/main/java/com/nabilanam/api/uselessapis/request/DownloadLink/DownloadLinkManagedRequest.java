package com.nabilanam.api.uselessapis.request.DownloadLink;

import com.nabilanam.api.uselessapis.model.ApiHost;
import lombok.*;

import java.net.URL;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DownloadLinkManagedRequest {

	private ApiHost apiHost;
	private URL apiUrl;
	private URL playlistApiUrl;
	private String clientSecret;
}

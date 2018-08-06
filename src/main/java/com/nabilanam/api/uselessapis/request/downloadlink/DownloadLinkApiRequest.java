package com.nabilanam.api.uselessapis.request.downloadlink;

import com.nabilanam.api.uselessapis.model.ApiHost;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URL;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DownloadLinkApiRequest {

	private ApiHost apiHost;
	private URL url;
}

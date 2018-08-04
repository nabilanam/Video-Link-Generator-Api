package com.nabilanam.api.uselessapis.model.DownloadLink;

import com.nabilanam.api.uselessapis.model.ApiHost;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.net.URL;

@Entity
@ToString
@Getter
@Setter
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"apiHost"})})
public class DownloadLinkManaged {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Enumerated(EnumType.STRING)
	private ApiHost apiHost;

	private URL apiUrl;

	private URL playlistApiUrl;

	private String clientSecret;

	public DownloadLinkManaged(ApiHost apiHost, URL apiUrl, URL playlistApiUrl, String clientSecret) {
		this.apiHost = apiHost;
		this.apiUrl = apiUrl;
		this.playlistApiUrl = playlistApiUrl;
		this.clientSecret = clientSecret;
	}
}

package com.nabilanam.downloader.youtube.model;

import lombok.Setter;
import lombok.ToString;

@Setter
@ToString
public class MuxedStream {
	private int itag;
	private String title;
	private String url;
	private String thumbnailUrl;
	private String signature;
	private ItagDescriptor itagDescriptor;

	public int getItag() {
		return itag;
	}

	public String getUrl() {
		if (signature == null || signature.isEmpty())
			return url;
		else
			return url + "&signature=" + signature;
	}

	public String getTitle(){
		return this.title;
	}

	public String getThumbnailUrl(){
		return this.thumbnailUrl;
	}

	public String getSignature() {
		return this.signature;
	}

	public ItagDescriptor getItagDescriptor() {
		return this.itagDescriptor;
	}
}

package com.nabilanam.downloader.youtube.model;

import com.nabilanam.downloader.shared.model.Container;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

@Data
@AllArgsConstructor
@ToString
public class ItagDescriptor {
	private Container container;
	private AudioEncoding audioEncoding;
	private VideoEncoding videoEncoding;
	private VideoQuality videoQuality;
}

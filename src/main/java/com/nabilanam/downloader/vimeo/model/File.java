package com.nabilanam.downloader.vimeo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class File {

	private List<Progressive> progressive;
}

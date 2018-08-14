package com.nabilanam.downloader.vimeo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.downloader.shared.contract.IdentityParser;
import com.nabilanam.downloader.shared.contract.VideoStreamProvider;
import com.nabilanam.downloader.shared.model.Container;
import com.nabilanam.downloader.shared.model.VideoStream;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import com.nabilanam.downloader.shared.util.RegexUtil;
import com.nabilanam.downloader.vimeo.model.Config;
import com.nabilanam.downloader.vimeo.model.Progressive;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class VimeoStreamProvider implements VideoStreamProvider {

	private final RegexUtil regexUtil;
	private final ObjectMapper objectMapper;
	private final IdentityParser vimeoIdentityParser;

	@Autowired
	public VimeoStreamProvider(RegexUtil regexUtil, IdentityParser vimeoIdentityParser, ObjectMapper objectMapper) {
		this.regexUtil = regexUtil;
		this.vimeoIdentityParser = vimeoIdentityParser;
		this.objectMapper = objectMapper;
	}

	@Override
	public VideoStreamContainer getVideoStreamContainer(URL url) throws ResourceNotFoundException {
		VideoStreamContainer container;
		try {
			vimeoIdentityParser.parseTrackId(url.toString());
			Document document = getJsoupDocument(url);
			Config config = objectMapper.readValue(getConfigUrl(document), Config.class);
			String title = config.getVideo().getTitle();
			String thumbUrl = config.getVideo().getThumbs().getBase();
			List<VideoStream> streams = new ArrayList<>();
			List<Progressive> list = config.getRequest().getFiles().getProgressive();
			for (Progressive progressive : list) {
				String quality = progressive.getQuality();
				String streamUrl = progressive.getUrl();
				VideoStream videoStream = new VideoStream(streamUrl, quality, Container.mp4);
				streams.add(videoStream);
			}
			container = new VideoStreamContainer(title, thumbUrl, streams);
		} catch (Exception ex) {
			throw new ResourceNotFoundException();
		}
		return container;
	}

	private URL getConfigUrl(Document document) throws Exception {
		String regex = "\"player\":\\{\"config_url\":\"(.+?)(?=\")";
		Optional<String> groupOne = regexUtil.getGroupOne(document.body().html(), regex);
		if (groupOne.isPresent()) {
			String url = groupOne.get().replace("\\", "");
			return new URL(url);
		} else throw new Exception();
	}

	private Document getJsoupDocument(URL url) throws IOException {
		return Jsoup.connect(url.toString()).get();
	}
}

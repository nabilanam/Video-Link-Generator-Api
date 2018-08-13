package com.nabilanam.downloader.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.downloader.shared.model.Container;
import com.nabilanam.downloader.shared.model.VideoStream;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import com.nabilanam.downloader.shared.model.VideoStreamProvider;
import com.nabilanam.downloader.shared.util.HttpResourceReader;
import com.nabilanam.downloader.shared.util.RegexUtil;
import com.nabilanam.downloader.twitter.model.Media;
import com.nabilanam.downloader.twitter.model.TwitterApiResponse;
import com.nabilanam.downloader.twitter.model.Variant;
import com.nabilanam.downloader.twitter.util.StatusIdentityParser;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TwitterStreamProvider implements VideoStreamProvider {

	private final RegexUtil regexUtil;
	private final StatusIdentityParser identityParser;
	private final HttpResourceReader httpResourceReader;
	private final ObjectMapper objectMapper;

	@Autowired
	public TwitterStreamProvider(RegexUtil regexUtil, StatusIdentityParser identityParser, HttpResourceReader httpResourceReader, ObjectMapper objectMapper) {
		this.regexUtil = regexUtil;
		this.identityParser = identityParser;
		this.httpResourceReader = httpResourceReader;
		this.objectMapper = objectMapper;
	}

	public VideoStreamContainer getVideoStreamContainer(URL url) throws ResourceNotFoundException {
		VideoStreamContainer container;
		try {
			String statusId = getStatusId(url.toString());
			String apiUrl = getCustomizedApiUrl(statusId);
			String videoUrl = getCustomizedVideoUrl(statusId);

			TwitterApiResponse apiResponse = getTwitterApiResponse(apiUrl, videoUrl);

			Media media;
			try {
				media = apiResponse.getExtended_entities().getMedia().get(0);
			} catch (RuntimeException ex) {
				throw new Exception("Video is hosted outside of twitter");
			}

			String text = apiResponse.getText();
			int lastIndex = text.lastIndexOf(" https:");

			String title = text.substring(0, lastIndex);
			String thumbnailUrl = media.getMedia_url_https().toString();
			List<VideoStream> videoStreams = getTwitterStreamList(media);

			container = new VideoStreamContainer(title, thumbnailUrl, videoStreams);
		} catch (Exception ex) {
			throw new ResourceNotFoundException();
		}
		return container;
	}

	private TwitterApiResponse getTwitterApiResponse(String apiUrl, String videoUrl) throws IOException {
		String authorizationKey = getAuthorizationKey(getVideoPlayerSourceCodeUrl(getJsoupDocument(videoUrl)));
		String json = httpResourceReader.read(apiUrl, new String[]{"Authorization"}, new String[]{authorizationKey});
		return objectMapper.readValue(json, TwitterApiResponse.class);
	}

	private List<VideoStream> getTwitterStreamList(Media media) {
		List<VideoStream> videoStreams = new ArrayList<>();
		List<Variant> variants = media.getVideo_info().getVariants();
		for (Variant variant : variants) {
			if (variant.getContent_type().contains("mp4")) {

				String url = variant.getUrl().toString();
				Optional<String> groupOne = regexUtil.getGroupOne(url, ".+?(\\d+x\\d+)(?=/\\w+)");
				String quality = groupOne.orElse("");

				VideoStream videoStream = new VideoStream(url, quality, Container.mp4);
				videoStreams.add(videoStream);
			}
		}
		return videoStreams;
	}

	private String getCustomizedApiUrl(String statusId) {
		return "https://api.twitter.com/1.1/statuses/show.json?id=" + statusId;
	}

	private String getAuthorizationKey(String sourceCodeUrl) throws IOException {
		String regex = "(Bearer.+?)(?=\",\"x-csrf-token\")";
		String data = httpResourceReader.read(sourceCodeUrl);
		Optional<String> groupOne = regexUtil.getGroupOne(data, regex);
		return groupOne.orElse("");
	}

	private String getVideoPlayerSourceCodeUrl(Document document) {
		Elements scripts = document.getElementsByTag("script");
		return scripts.first().attr("src");
	}

	private String getCustomizedVideoUrl(String statusId) {
		return "https://twitter.com/i/videos/tweet/" + statusId;
	}

	private String getStatusId(String url) {
		return identityParser.parseId(url);
	}

	private Document getJsoupDocument(String url) throws IOException {
		return Jsoup.connect(url).get();
	}
}

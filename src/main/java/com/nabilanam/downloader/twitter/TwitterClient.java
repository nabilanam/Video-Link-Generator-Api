package com.nabilanam.downloader.twitter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabilanam.downloader.twitter.model.Container;
import com.nabilanam.downloader.twitter.model.TwitterStream;
import com.nabilanam.downloader.twitter.model.TwitterStreamsWrapper;
import com.nabilanam.downloader.twitter.model.twitterapi.Media;
import com.nabilanam.downloader.twitter.model.twitterapi.TwitterApiResponse;
import com.nabilanam.downloader.twitter.model.twitterapi.Variant;
import com.nabilanam.downloader.twitter.util.StatusIdentityParser;
import com.nabilanam.downloader.util.HttpResourceReader;
import com.nabilanam.downloader.util.RegexUtil;
import org.aspectj.weaver.ast.Var;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class TwitterClient {

	private final RegexUtil regexUtil;
	private final StatusIdentityParser identityParser;
	private final HttpResourceReader httpResourceReader;
	private final ObjectMapper objectMapper;

	@Autowired
	public TwitterClient(RegexUtil regexUtil, StatusIdentityParser identityParser, HttpResourceReader httpResourceReader, ObjectMapper objectMapper) {
		this.regexUtil = regexUtil;
		this.identityParser = identityParser;
		this.httpResourceReader = httpResourceReader;
		this.objectMapper = objectMapper;
	}

	public TwitterStreamsWrapper getTwitterStreamWrapper(String url) throws Exception {
		String statusId = getStatusId(url);
		String apiUrl = getCustomizedApiUrl(statusId);
		String videoUrl = getCustomizedVideoUrl(statusId);

		TwitterApiResponse apiResponse = getTwitterApiResponse(apiUrl, videoUrl);
		TwitterStreamsWrapper streamsWrapper = new TwitterStreamsWrapper();

		String text = apiResponse.getText();
		int lastIndex = text.lastIndexOf(" https:");
		streamsWrapper.setText(text.substring(0,lastIndex));

		Media media = null;
		try {
			media = apiResponse.getExtended_entities().getMedia().get(0);
		} catch (RuntimeException ex){
			throw new Exception("Video is hosted outside of twitter");
		}
		streamsWrapper.setThumbnailUrl(media.getMedia_url_https().toString());

		List<TwitterStream> streams = getTwitterStreams(media);
		streamsWrapper.setStreams(streams);
		return streamsWrapper;
	}

	private TwitterApiResponse getTwitterApiResponse(String apiUrl, String videoUrl) throws IOException {
		String authorizationKey = getAuthorizationKey(getVideoPlayerSourceCodeUrl(getJsoupDocument(videoUrl)));
		String json = httpResourceReader.read(apiUrl, new String[]{"Authorization"}, new String[]{authorizationKey});
		return objectMapper.readValue(json, TwitterApiResponse.class);
	}

	private List<TwitterStream> getTwitterStreams(Media media) {
		List<TwitterStream> streams = new ArrayList<>();
		List<Variant> variants = media.getVideo_info().getVariants();
		for (Variant variant : variants) {
			if (variant.getContent_type().contains("mp4")) {

				TwitterStream stream = new TwitterStream();
				stream.setUrl(variant.getUrl().toString());
				stream.setContainer(Container.mp4);

				Optional<String> groupOne = regexUtil.getGroupOne(variant.getUrl().toString(), ".+?(\\d+x\\d+)(?=/\\w+)");
				groupOne.ifPresent(stream::setResolution);
				streams.add(stream);
			}
		}
		return streams;
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

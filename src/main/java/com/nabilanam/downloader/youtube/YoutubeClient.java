package com.nabilanam.downloader.youtube;

import com.nabilanam.downloader.youtube.model.*;
import com.nabilanam.downloader.youtube.util.IdentityFinderUtil;
import com.nabilanam.downloader.youtube.util.MuxedStreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class YoutubeClient {

	private final MuxedStreamUtils muxedStreamUtils;
	private final IdentityFinderUtil identityFinderUtil;

	@Autowired
	public YoutubeClient(MuxedStreamUtils muxedStreamUtils, IdentityFinderUtil identityFinderUtil) {
		this.muxedStreamUtils = muxedStreamUtils;
		this.identityFinderUtil = identityFinderUtil;
	}

	public YoutubeStreamsWrapper getDownloadLink(URL url) throws Exception {
		String videoId = identityFinderUtil.parseVideoId(url.toString());
		List<MuxedStream> muxedStreams = muxedStreamUtils.getMuxedStreams(videoId);
		List<YoutubeStream> youtubeStreams = new ArrayList<>();
		for (MuxedStream stream : muxedStreams) {
			YoutubeStream youtubeStream = getYoutubeSingleFromMuxedStream(stream);
			youtubeStreams.add(youtubeStream);
		}
		MuxedStream stream = muxedStreams.get(0);
		String title = stream.getTitle();
		String thumbnailUrl = stream.getThumbnailUrl();
		return new YoutubeStreamsWrapper(title,thumbnailUrl,youtubeStreams);
	}

	private YoutubeStream getYoutubeSingleFromMuxedStream(MuxedStream stream) {
		String url = stream.getUrl();
		ItagDescriptor itagDescriptor = stream.getItagDescriptor();
		String container = getVideoContainer(itagDescriptor.getContainer());
		String videoQuality = getVideoQuality(itagDescriptor.getVideoQuality());
		return new YoutubeStream(url, container, videoQuality);
	}

	private String getVideoContainer(Container container) {
		String containerStr = "";
		switch (container) {
			case mp4:
				containerStr = "mp4";
				break;
			case m4a:
				containerStr = "m4a";
				break;
			case webm:
				containerStr = "webm";
				break;
			case tgpp:
				containerStr = "3gpp";
				break;
			case flv:
				containerStr = "flv";
				break;
		}
		return containerStr;
	}

	private String getVideoQuality(VideoQuality videoQuality) {
		String videoQualityStr = "";
		switch (videoQuality) {
			case Low144:
				videoQualityStr = "144p";
				break;
			case Low240:
				videoQualityStr = "240p";
				break;
			case Medium360:
				videoQualityStr = "360p";
				break;
			case Medium480:
				videoQualityStr = "480p";
				break;
			case High720:
				videoQualityStr = "720p";
				break;
			case High1080:
				videoQualityStr = "1080p";
				break;
			case High1440:
				videoQualityStr = "1440p";
				break;
			case High2160:
				videoQualityStr = "2160p";
				break;
			case High3072:
				videoQualityStr = "3072p";
				break;
			case High4320:
				videoQualityStr = "4320p";
				break;
		}
		return videoQualityStr;
	}
}

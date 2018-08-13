package com.nabilanam.downloader.youtube;

import com.nabilanam.api.uselessapis.exception.ResourceNotFoundException;
import com.nabilanam.downloader.shared.model.VideoStream;
import com.nabilanam.downloader.shared.model.VideoStreamContainer;
import com.nabilanam.downloader.shared.model.VideoStreamProvider;
import com.nabilanam.downloader.youtube.model.ItagDescriptor;
import com.nabilanam.downloader.youtube.model.MuxedStream;
import com.nabilanam.downloader.youtube.model.VideoQuality;
import com.nabilanam.downloader.youtube.util.IdentityFinderUtil;
import com.nabilanam.downloader.youtube.util.MuxedStreamUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

@Component
public class YoutubeStreamProvider implements VideoStreamProvider {

	private final MuxedStreamUtils muxedStreamUtils;
	private final IdentityFinderUtil identityFinderUtil;

	@Autowired
	public YoutubeStreamProvider(MuxedStreamUtils muxedStreamUtils, IdentityFinderUtil identityFinderUtil) {
		this.muxedStreamUtils = muxedStreamUtils;
		this.identityFinderUtil = identityFinderUtil;
	}

	public VideoStreamContainer getVideoStreamContainer(URL url) throws ResourceNotFoundException {
		VideoStreamContainer container;
		try {
			String videoId = identityFinderUtil.parseVideoId(url.toString());
			List<MuxedStream> muxedStreams = muxedStreamUtils.getMuxedStreams(videoId);
			List<VideoStream> videoStreams = new ArrayList<>();
			for (MuxedStream stream : muxedStreams) {
				VideoStream videoStream = getVideoStream(stream);
				videoStreams.add(videoStream);
			}
			MuxedStream stream = muxedStreams.get(0);
			String title = stream.getTitle();
			String thumbnailUrl = stream.getThumbnailUrl();
			container = new VideoStreamContainer(title, thumbnailUrl, videoStreams);
		} catch (Exception ex) {
			throw new ResourceNotFoundException();
		}
		return container;
	}

	private VideoStream getVideoStream(MuxedStream stream) {
		String url = stream.getUrl();
		ItagDescriptor itagDescriptor = stream.getItagDescriptor();
		String videoQuality = getVideoQuality(itagDescriptor.getVideoQuality());
		return new VideoStream(url, videoQuality, itagDescriptor.getContainer());
	}

	private String getVideoQuality(VideoQuality quality) {
		String qualityStr = "";
		switch (quality) {
			case Low144:
				qualityStr = "144p";
				break;
			case Low240:
				qualityStr = "240p";
				break;
			case Medium360:
				qualityStr = "360p";
				break;
			case Medium480:
				qualityStr = "480p";
				break;
			case High720:
				qualityStr = "720p";
				break;
			case High1080:
				qualityStr = "1080p";
				break;
			case High1440:
				qualityStr = "1440p";
				break;
			case High2160:
				qualityStr = "2160p";
				break;
			case High3072:
				qualityStr = "3072p";
				break;
			case High4320:
				qualityStr = "4320p";
				break;
		}
		return qualityStr;
	}
}

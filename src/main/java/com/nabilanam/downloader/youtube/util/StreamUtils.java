package com.nabilanam.downloader.youtube.util;

import com.nabilanam.downloader.shared.model.Container;
import com.nabilanam.downloader.youtube.model.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class StreamUtils {
	private static HashMap<Integer, ItagDescriptor> itagMap;

	ItagDescriptor getItagDescriptor(int itag){
		return itagMap.get(itag);
	}

	static {
		itagMap = new HashMap<>();
		itagMap.put(5,new ItagDescriptor(Container.flv, AudioEncoding.mp3, VideoEncoding.h263, VideoQuality.Low144));
		itagMap.put(6, new ItagDescriptor(Container.flv, AudioEncoding.mp3, VideoEncoding.h263, VideoQuality.Low240));
		itagMap.put(13, new ItagDescriptor(Container.tgp, AudioEncoding.aac, VideoEncoding.mp4v, VideoQuality.Low144));
		itagMap.put(17, new ItagDescriptor(Container.tgp, AudioEncoding.aac, VideoEncoding.mp4v, VideoQuality.Low144));
		itagMap.put(18, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.Medium360));
		itagMap.put(22, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.High720));
		itagMap.put(34, new ItagDescriptor(Container.flv, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.Medium360));
		itagMap.put(35, new ItagDescriptor(Container.flv, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.Medium480));
		itagMap.put(36, new ItagDescriptor(Container.tgp, AudioEncoding.aac, VideoEncoding.mp4v, VideoQuality.Low240));
		itagMap.put(37, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.High1080));
		itagMap.put(38, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.High3072));
		itagMap.put(43, new ItagDescriptor(Container.webm, AudioEncoding.vorbis, VideoEncoding.vp8, VideoQuality.Medium360));
		itagMap.put(44, new ItagDescriptor(Container.webm, AudioEncoding.vorbis, VideoEncoding.vp8, VideoQuality.Medium480));
		itagMap.put(45, new ItagDescriptor(Container.webm, AudioEncoding.vorbis, VideoEncoding.vp8, VideoQuality.High720));
		itagMap.put(46, new ItagDescriptor(Container.webm, AudioEncoding.vorbis, VideoEncoding.vp8, VideoQuality.High1080));
		itagMap.put(59, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.Medium480));
		itagMap.put(78, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.Medium480));
		itagMap.put(82, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.Medium360));
		itagMap.put(83, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.Medium480));
		itagMap.put(84, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.High720));
		itagMap.put(85, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.High1080));
		itagMap.put(91, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.Low144));
		itagMap.put(92, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.Low240));
		itagMap.put(93, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.Medium360));
		itagMap.put(94, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.Medium480));
		itagMap.put(95, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.High720));
		itagMap.put(96, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.High1080));
		itagMap.put(100, new ItagDescriptor(Container.webm, AudioEncoding.vorbis, VideoEncoding.vp8, VideoQuality.Medium360));
		itagMap.put(101, new ItagDescriptor(Container.webm, AudioEncoding.vorbis, VideoEncoding.vp8, VideoQuality.Medium480));
		itagMap.put(102, new ItagDescriptor(Container.webm, AudioEncoding.vorbis, VideoEncoding.vp8, VideoQuality.High720));
		itagMap.put(132, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.Low240));
		itagMap.put(151, new ItagDescriptor(Container.mp4, AudioEncoding.aac, VideoEncoding.h264, VideoQuality.Low144));

		// Video-only (mp4)
		itagMap.put(133, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.Low240));
		itagMap.put(134, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.Medium360));
		itagMap.put(135, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.Medium480));
		itagMap.put(136, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.High720));
		itagMap.put(137, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.High1080));
		itagMap.put(138, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.High4320));
		itagMap.put(160, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.Low144));
		itagMap.put(212, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.Medium480));
		itagMap.put(213, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.Medium480));
		itagMap.put(214, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.High720));
		itagMap.put(215, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.High720));
		itagMap.put(216, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.High1080));
		itagMap.put(217, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.High1080));
		itagMap.put(264, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.High1440));
		itagMap.put(266, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.High2160));
		itagMap.put(298, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.High720));
		itagMap.put(299, new ItagDescriptor(Container.mp4, null, VideoEncoding.h264, VideoQuality.High1080));

		// Video-only (webm)
		itagMap.put(167, new ItagDescriptor(Container.webm, null, VideoEncoding.vp8, VideoQuality.Medium360));
		itagMap.put(168, new ItagDescriptor(Container.webm, null, VideoEncoding.vp8, VideoQuality.Medium480));
		itagMap.put(169, new ItagDescriptor(Container.webm, null, VideoEncoding.vp8, VideoQuality.High720));
		itagMap.put(170, new ItagDescriptor(Container.webm, null, VideoEncoding.vp8, VideoQuality.High1080));
		itagMap.put(218, new ItagDescriptor(Container.webm, null, VideoEncoding.vp8, VideoQuality.Medium480));
		itagMap.put(219, new ItagDescriptor(Container.webm, null, VideoEncoding.vp8, VideoQuality.Medium480));
		itagMap.put(242, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.Low240));
		itagMap.put(243, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.Medium360));
		itagMap.put(244, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.Medium480));
		itagMap.put(245, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.Medium480));
		itagMap.put(246, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.Medium480));
		itagMap.put(247, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.High720));
		itagMap.put(248, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.High1080));
		itagMap.put(271, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.High1440));
		itagMap.put(272, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.High2160));
		itagMap.put(278, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.Low144));
		itagMap.put(302, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.High720));
		itagMap.put(303, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.High1080));
		itagMap.put(308, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.High1440));
		itagMap.put(313, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.High2160));
		itagMap.put(315, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.High2160));
		itagMap.put(330, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.Low144));
		itagMap.put(331, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.Low240));
		itagMap.put(332, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.Medium360));
		itagMap.put(333, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.Medium480));
		itagMap.put(334, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.High720));
		itagMap.put(335, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.High1080));
		itagMap.put(336, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.High1440));
		itagMap.put(337, new ItagDescriptor(Container.webm, null, VideoEncoding.vp9, VideoQuality.High2160));

		// Audio-only (mp4)
		itagMap.put(139, new ItagDescriptor(Container.m4a, AudioEncoding.aac, null, null));
		itagMap.put(140, new ItagDescriptor(Container.m4a, AudioEncoding.aac, null, null));
		itagMap.put(141, new ItagDescriptor(Container.m4a, AudioEncoding.aac, null, null));
		itagMap.put(256, new ItagDescriptor(Container.m4a, AudioEncoding.aac, null, null));
		itagMap.put(258, new ItagDescriptor(Container.m4a, AudioEncoding.aac, null, null));
		itagMap.put(325, new ItagDescriptor(Container.m4a, AudioEncoding.aac, null, null));
		itagMap.put(328, new ItagDescriptor(Container.m4a, AudioEncoding.aac, null, null));

		// Audio-only (webm)
		itagMap.put(171, new ItagDescriptor(Container.webm, AudioEncoding.vorbis, null, null));
		itagMap.put(172, new ItagDescriptor(Container.webm, AudioEncoding.vorbis, null, null));
		itagMap.put(249, new ItagDescriptor(Container.webm, AudioEncoding.opus, null, null));
		itagMap.put(250, new ItagDescriptor(Container.webm, AudioEncoding.opus, null, null));
		itagMap.put(251, new ItagDescriptor(Container.webm, AudioEncoding.opus, null, null));
	}
}

package com.nabilanam.api.uselessapis.service.downloadlink.youtube;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class YoutubeUtils implements ApplicationRunner {
	@Override
	public void run(ApplicationArguments args) throws Exception {
		System.out.println(getVideoInfo("yIVRs6YSbOM","17745"));
	}

	String getVideoInfo(String videoId, String sts) throws IOException {
		String url = "https://www.youtube.com/get_video_info?video_id="+videoId+"&sts="+sts+"&hl=en";
		Document document = Jsoup.connect(url).get();
		return document.data();
	}
}

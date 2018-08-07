package com.nabilanam.api.uselessapis.service.downloadlink.youtube;

import com.nabilanam.api.uselessapis.config.Beans;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.Optional;
import java.util.logging.Logger;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {YoutubeClient.class,YoutubeIdFinder.class,Beans.class})
public class YoutubeClientTest {

	@Autowired
	private YoutubeClient youtubeClient;

	private static Logger LOGGER = Logger.getLogger(YoutubeClientTest.class.getName());

//	@Test
//	public void whenGivenEmbedUrl_thenReturnsSTS() throws IOException {
//		//given
//		String videoUrl = "https://www.youtube.com/watch?v=yIVRs6YSbOM";
//
//		//when
//		Optional<Integer> optionalSts = youtubeClient.getSts(videoUrl);
//		int sts = 0;
//		if (optionalSts.isPresent()){
//			sts = optionalSts.get();
//			LOGGER.info("sts: "+sts);
//		}
//
//		//then
//		assertThat(sts).isGreaterThan(0);
//	}
}

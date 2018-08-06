package com.nabilanam.api.uselessapis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nabilanam.api.uselessapis.service.downloadlink.youtube.YoutubeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Beans {

	@Bean
	public ObjectMapper ObjectMapper() {
		return new ObjectMapper();
	}

	@Bean
	public YoutubeClient YoutubeClient() {
		return new YoutubeClient();
	}
}

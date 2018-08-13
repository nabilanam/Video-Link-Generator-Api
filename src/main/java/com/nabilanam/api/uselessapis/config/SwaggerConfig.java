package com.nabilanam.api.uselessapis.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger.web.UiConfigurationBuilder;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.nabilanam.api.uselessapis.controller.download"))
				.paths(PathSelectors.ant("/service/**"))
				.build()
				.apiInfo(apiInfo());
	}

	private ApiInfo apiInfo() {
		return new ApiInfo(
				"Useless Apis",
				"100% not so useless apis",
				"v1",
				"",
				new Contact("Nabil Anam", "", "nabilcoding@gmail.com"),
				"",
				"",
				Collections.emptyList()
		);
	}

	@Bean
	UiConfiguration uiConfig() {
		 return UiConfigurationBuilder.builder().supportedSubmitMethods(UiConfiguration.Constants.NO_SUBMIT_METHODS).build();
	}
}

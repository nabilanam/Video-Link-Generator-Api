package com.nabilanam.api.uselessapis.config;

import springfox.documentation.spring.web.paths.AbstractPathProvider;

public class ExtendRelativePathProvider extends AbstractPathProvider {
	public static final String ROOT = "/";

	@Override
	protected String applicationPath() {
		return ROOT;
	}

	@Override
	protected String getDocumentationPath() {
		return "/apidoc";
	}
}

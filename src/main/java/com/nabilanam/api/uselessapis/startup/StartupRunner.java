package com.nabilanam.api.uselessapis.startup;

import com.nabilanam.api.uselessapis.service.security.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupRunner implements ApplicationRunner {

	private final UserSecurityService userSecurityService;

	@Autowired
	public StartupRunner(UserSecurityService userSecurityService) {
		this.userSecurityService = userSecurityService;
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {
		userSecurityService.setUselessAdmin();
	}
}

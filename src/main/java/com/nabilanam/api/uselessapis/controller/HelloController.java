package com.nabilanam.api.uselessapis.controller;

import com.nabilanam.api.uselessapis.service.security.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

	private final UserSecurityService userSecurityService;

	@Autowired
	public HelloController(UserSecurityService userSecurityService) {
		this.userSecurityService = userSecurityService;
	}

	@GetMapping("/")
	public String getRoot(){
		return "hi";
	}

	@PostMapping("/api/hello")
	public String getHello() {
			return "hello world";
	}
}

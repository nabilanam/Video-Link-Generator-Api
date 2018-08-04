package com.nabilanam.api.uselessapis.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
public class HelloController {

	@GetMapping("/hello")
	public Map<String, String> getHello() {
		return Collections.singletonMap("msg","hello!");
	}
}

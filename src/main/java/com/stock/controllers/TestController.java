package com.stock.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.stock.StockManagementConstants;

@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
public class TestController {

	@GetMapping("/test")
	String test() {
		return "Hello from Stock Management!";
	}
	
}

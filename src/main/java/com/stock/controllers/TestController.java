package com.stock.controllers;

import com.stock.StockManagementConstants;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(StockManagementConstants.API_VERSION)
public class TestController {

	@GetMapping("/test")
	String test() {
		return StockManagementConstants.DESCRIPTION;
	}
	
}

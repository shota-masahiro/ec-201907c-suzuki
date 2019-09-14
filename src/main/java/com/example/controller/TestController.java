package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.service.ExecuteReviewService;

@Controller
@RequestMapping("/test")
public class TestController {
	
	@Autowired
	private ExecuteReviewService executeReviewService;
	
	@RequestMapping("")
	public String index() {
		String star = executeReviewService.averageReview(1);
		System.out.println(star);
		return "test";
	}
	
	
	
}

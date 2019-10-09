package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.repository.OrderRepository;

@Controller
@RequestMapping("/repo")
public class TestController {
	
	@Autowired
	private OrderRepository orderRepository;
	
	@RequestMapping("")
	public String index() {
		orderRepository.newFindByUserId(1, true).forEach(System.out::println);
		return "test";
	}

}

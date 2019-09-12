package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.service.ExecuteShoppingCartService;

@Controller
@RequestMapping("/orderHistory")
public class OrderHistoryController {

	@Autowired
	private ExecuteShoppingCartService executeShoppingCartService;

	@RequestMapping("")
	public String index(Model model) {
		Order order = executeShoppingCartService.findByUserId(1);
		model.addAttribute("order", order);
		System.out.println(order);
		return "order_history";
	}

}

package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.service.ExecuteShoppingCartService;

@Controller
@RequestMapping("/orderHistory")
public class OrderHistoryController {

	@Autowired
	private ExecuteShoppingCartService executeShoppingCartService;

	@RequestMapping("")
	public String index(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Order order = executeShoppingCartService.findByUserId(loginUser.getUser().getId());
		model.addAttribute("order", order);
		return "order_history";
	}

}

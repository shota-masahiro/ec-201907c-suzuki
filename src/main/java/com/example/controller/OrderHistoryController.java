package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.service.ExecuteShoppingCartService;

/**
 * 注文履歴画面を操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/orderHistory")
public class OrderHistoryController {

	@Autowired
	private ExecuteShoppingCartService executeShoppingCartService;


	/**
	 * 注文履歴画面を出力します.
	 * 
	 * @param loginUser ログインユーザ情報
	 * @param model     リクエストスコープ
	 * @return          注文履歴画面
	 */
	@RequestMapping("")
	public String index(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Order order = executeShoppingCartService.findByUserId(loginUser.getUser().getId());
		model.addAttribute("order", order);
		return "order_history";
	}
	
	private List<Integer> calcNumber(Model model, Page<Order> orderPage) {
		int totalPages = orderPage.getTotalPages();
		List<Integer> pageNumbers = null;
		if (totalPages > 0) {
			pageNumbers = new ArrayList<Integer>();
			for (int i = 1; i < totalPages; i++) {
				pageNumbers.add(i);
			}
		}
		return pageNumbers;
	}

}

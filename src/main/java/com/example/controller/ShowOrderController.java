package com.example.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.enums.PaymentMethod;
import com.example.service.ExecuteShoppingCartService;

/**
 * 注文確認画面を表示します.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/showOrder")
public class ShowOrderController {
	
	@Autowired
	private ExecuteShoppingCartService executeShoppingCartService;
	
	/**
	 * 注文確認画面を表示します.
	 * 
	 * @param orderId 注文ID
	 * @param model   リクエストスコープ
	 * @return        注文確認画面
	 */
	@RequestMapping("")
	public String index(String orderId, Model model) {

		Order checkOrder = executeShoppingCartService.findByOrderId(Integer.parseInt(orderId));

		if (checkOrder.getOrderItemList().size() == 0) {
			return "forward:/showItemList";
		}

		List<List<Integer>> timeAllList = new ArrayList<>();
		List<Integer> time3List = new ArrayList<>();
		for (int i = 10; i <= 18; i++) {
			time3List.add(i);
			if (i % 3 == 0) {
				timeAllList.add(time3List);
				time3List = new ArrayList<>();
			}
		}
		model.addAttribute("timeAllList", timeAllList);

		Map<Integer, String> paymentMap = new LinkedHashMap<>();
		for (PaymentMethod paymentMethod : PaymentMethod.values()) {
			paymentMap.put(paymentMethod.getKey(), paymentMethod.getValue());
		}
		model.addAttribute("paymentMap", paymentMap);

		Order order = executeShoppingCartService.findByOrderId(Integer.parseInt(orderId));
		model.addAttribute("order", order);

		return "order_confirm";
	}

}

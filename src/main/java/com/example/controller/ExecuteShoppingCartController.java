package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Order;
import com.example.form.ExecuteShoppingCartForm;
import com.example.repository.OrderRepository;
import com.example.service.ExecuteShoppingCartService;

/**
 * ショッピングカートを操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/executeShoppingCart")
public class ExecuteShoppingCartController {

	@Autowired
	private ExecuteShoppingCartService executeShoppingCartService;

	@ModelAttribute
	public ExecuteShoppingCartForm setUpExecuteShoppingCartForm() {
		return new ExecuteShoppingCartForm();
	}


	/**
	 * ショッピングカート画面を出力します.
	 * 
	 * @param orderId 注文ID
	 * @param model   リクエストスコープ
	 * @return        ショッピングカート画面
	 */
	public String index(Integer orderId, Model model) {
		if (orderId == null || orderId == 0) {
			return "cart_list";
		}
		Order order = executeShoppingCartService.findByOrderId(orderId);
		model.addAttribute("order", order);
		return "cart_list";
	}


	/**
	 * ショッピングカートに商品を入れる追加する処理
	 * 
	 * @param form  リクエストパラメータ
	 * @param model リクエストスコープ
	 * @return      ショッピングカート画面
	 */
	@RequestMapping("toInCart")
	public String toInCart(ExecuteShoppingCartForm form, Model model) {

		System.out.println(form);
		Order order = executeShoppingCartService.insert(form);
		model.addAttribute("order", order);
		System.out.println(order);

		return "cart_list";
	}


	/**
	 * 削除処理をします.
	 * 
	 * @param orderId     注文ID
	 * @param orderItemId 注文商品ID
	 * @param model       リクエストパラメータ
	 * @return            ショッピングカート画面
	 */
	@RequestMapping("/delete")
	public String delete(String orderId, String orderItemId, Model model) {
		executeShoppingCartService.delete(Integer.parseInt(orderItemId));
		return index(Integer.parseInt(orderId), model);
	}

}
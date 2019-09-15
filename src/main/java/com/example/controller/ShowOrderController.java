package com.example.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.context.Context;

import com.example.common.SendMailService;
import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.User;
import com.example.enums.PaymentMethod;
import com.example.form.OrderItemForm;
import com.example.service.ExecuteShoppingCartService;
import com.example.service.RegisterUserService;

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

	@Autowired
	private RegisterUserService registerUserService;

	@Autowired
	private SendMailService sendMailService;


	@Autowired
	private OrderItemForm setUpOrderItemForm() {
		return new OrderItemForm();
	}

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

		User user = registerUserService.load(6);
		model.addAttribute("user", user);

		Order order = executeShoppingCartService.findByOrderId(Integer.parseInt(orderId));
		model.addAttribute("order", order);

		return "order_confirm";
	}



	/**
	 * 注文完了処理を行います.
	 * 
	 * @param form  リクエストパラメータ
	 * @param model リクエストスコープ
	 * @return      注文完了画面
	 */
	@RequestMapping("/toOrder")
	public String toOrder(
			OrderItemForm form,
			Model model) {

		//送信メールの準備
		Context context = new Context();

		//User情報の取得
		Order order = executeShoppingCartService.findByOrderId(form.getIntOrderId());

		//ユーザー名をsetする
		context.setVariable("user_name", form.getDestinationName());

		//商品名を格納
		List<OrderItem> orderItemList = order.getOrderItemList();
		List<Item> itemList = new ArrayList<>();
		for (OrderItem orderItem : orderItemList) {
			itemList.add(orderItem.getItem());
		}
		List<String> nameList = new ArrayList<>();
		for (Item item : itemList) {
			nameList.add(item.getName());
		}
		context.setVariable("name_list", nameList);

		//金額を格納
		context.setVariable("total_price", order.getTotalPrice());

		//支払方法を格納
		if (order.getPaymentMethod().equals(1)) {
			context.setVariable("paymentMethod", "代金引換");
		} else if (order.getPaymentMethod().equals(2)) {
			context.setVariable("paymentMethod", "クレジットカード");
		}

		//配達日を格納
		context.setVariable("delivery_time", order.getDeliveryTime());

		//メール送信処理
		sendMailService.sendMail(context, form.getDestinationEmail());

		executeShoppingCartService.update(form);

		return "order_finished";
	}

}
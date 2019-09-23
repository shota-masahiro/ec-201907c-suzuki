package com.example.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;

import com.example.common.SendMailService;
import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.form.OrderItemForm;

/**
 * メール送信を行うサービス.
 * 
 * @author shota.suzuki
 *
 */
@Service
public class SendingMaiService {

	@Autowired
	private ExecuteShoppingCartService executeShoppingCartService;

	@Autowired
	private SendMailService sendMailService;

	/**
	 * メール送信処理を行います.
	 * 
	 * @param form リクエストパラメータ
	 */
	@Async
	public void run(OrderItemForm form) {

		//送信メールの準備
		Context context = new Context();
		Order order = executeShoppingCartService.findByOrderId(form.getIntOrderId());

		//1ユーザー名をsetする
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

	}

}

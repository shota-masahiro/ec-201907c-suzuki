package com.example.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.form.ExecuteShoppingCartForm;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;

@Service
@Transactional
public class ExecuteShoppingCartService {

	@Autowired
	private OrderRepository orderRepository;

	@Autowired
	private OrderItemRepository orderItemRepository;

	@Autowired
	private OrderToppingRepository orderToppingRepository;


	/**
	 * 挿入処理をします.
	 * カートの中が空の時は3つのテーブルにデータを挿入し、
	 * カートの中が有の時は2つのテーブルにデータを挿入します.
	 * 
	 * @param form リクエストパラメータ
	 * @return 注文情報
	 */
	@SuppressWarnings("null")
	public Order insert(ExecuteShoppingCartForm form) {

		Order checkOrder = orderRepository.findByUserIdAndStatus(form.getIntUserId(), form.getIntStatus());

		OrderItem orderItem = null;
		Integer orderId = null;

		if (checkOrder == null) { //カートの中身が空の処理
			Order order = new Order();
			order.setUserId(form.getIntUserId());
			order.setStatus(form.getIntStatus());
			order.setTotalPrice(0);
			orderId = orderRepository.insert(order);

			orderItem = createOrderItem(form, orderId);
		} else { // カートの中身が有の処理
			orderId = checkOrder.getId();
			orderItem = createOrderItem(form, checkOrder.getId());
		}
		Integer orderItemId = orderItemRepository.insert(orderItem);

		OrderTopping orderTopping = null;
		if (form.getToppingIdList() == null) {
			form.setToppingIdList(new ArrayList<Integer>());
		}
		for (Integer toppingId : form.getToppingIdList()) {
			orderTopping = new OrderTopping();
			orderTopping.setToppingId(toppingId);
			orderTopping.setOrderItemId(orderItemId);
			orderToppingRepository.insert(orderTopping);
		}
		System.out.println(orderId);
		return orderRepository.findByOrderId(orderId);
	}


	/**
	 * OrderItemオブジェクトを生成する処理を行います.
	 * 
	 * @param form リクエストパラメータ
	 * @param orderId 主キー
	 * @return OrderItemオブジェクト
	 */
	private OrderItem createOrderItem(ExecuteShoppingCartForm form, Integer orderId) {
		OrderItem orderItem = new OrderItem();
		orderItem.setItemId(form.getIntItemId());
		orderItem.setOrderId(orderId);
		orderItem.setQuantity(form.getIntQuantity());
		orderItem.setSize(form.getCharSize());
		return orderItem;
	}


	/**
	 * 注文情報を取得します.
	 * 
	 * @param orderId 注文ID
	 * @return Orderオブジェクト
	 */
	public Order findByOrderId(Integer orderId) {
		return orderRepository.findByOrderId(orderId);
	}
	
	
	public void delete(Integer orderItemId) {
		orderToppingRepository.delete(orderItemId);
		orderItemRepository.delete(orderItemId);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}

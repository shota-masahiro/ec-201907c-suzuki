package com.example.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.enums.PaymentMethod;
import com.example.form.ExecuteShoppingCartForm;
import com.example.form.OrderItemForm;
import com.example.repository.OrderItemRepository;
import com.example.repository.OrderRepository;
import com.example.repository.OrderToppingRepository;

/**
 * ショッピングカート内の処理を操作するサービス.
 * 
 * @author shota.suzuki
 *
 */
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
	 * @return     注文情報
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
		return orderRepository.findByOrderId(orderId);
	}


	/**
	 * OrderItemオブジェクトを生成する処理を行います.
	 * 
	 * @param form    リクエストパラメータ
	 * @param orderId 主キー
	 * @return        OrderItemオブジェクト
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
	 * @return        Orderオブジェクト
	 */
	public Order findByOrderId(Integer orderId) {
		return orderRepository.findByOrderId(orderId);
	}


	/**
	 * 注文情報を取得します.
	 * 
	 * @param userId ユーザID
	 * @return       Orderオブジェクト
	 */
	public Order findByUserId(Integer userId) {
		return orderRepository.findByUserId(userId);
	}


	/**
	 * 注文情報を取得します.
	 * 
	 * @param userId ユーザID
	 * @return       Orderオブジェクト
	 */
	public Order findByUserId2(Integer userId) {
		return orderRepository.findByUserId2(userId);
	}


	/**
	 * 削除処理をします.
	 * 
	 * @param orderItemId 注文商品ID
	 */
	public void delete(Integer orderItemId) {
		orderToppingRepository.delete(orderItemId);
		orderItemRepository.delete(orderItemId);
	}


	/**
	 * 更新処理をします.
	 * 
	 * @param form リクエストパラメータ
	 */
	@SuppressWarnings("unlikely-arg-type")
	public void update(OrderItemForm form) {
		Order order = orderRepository.findByOrderId(form.getIntOrderId());
		BeanUtils.copyProperties(form, order);
		Date date = new Date();
		order.setOrderDate(date);
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		StringBuilder orderNumber = new StringBuilder(sdf.format(date));
		orderNumber.append(orderRepository.getOrderNumber());
		
		if (PaymentMethod.CASHONDELIVERY.getKey().equals(form.getPaymentMethod())) {
			order.setStatus(1);
		} else if (PaymentMethod.CREDITCARD.getKey().equals(form.getPaymentMethod())) {
			order.setStatus(2);
		} else {
			order.setStatus(3);
		}
		
		order.setDeliveryTime(form.getTimestampDeliveryDayTime());
		order.setPaymentMethod(form.getIntePaymentMethod());
		order.setTotalPrice(form.getIntTotalPrice());
		order.setOrderNumber(orderNumber.toString());
		orderRepository.update(order);
	}


	/**
	 * 更新処理をします.
	 * 
	 * @param orderId 注文情報ID
	 * @param userId  tokenID
	 */
	public void update2(Integer orderId, Integer userId) {
		orderRepository.update2(orderId, userId);
	}

}

package com.example.domain;

import java.util.Date;
import java.util.List;

/**
 * 注文情報を表すドメイン.
 * 
 * @author shota.suzuki
 *
 */
public class Order {
	
	/** 注文ID */
	private Integer id;
	
	/** ユーザID */
	private Integer userId;
	
	/** 状態 */
	private Integer status;
	
	/** 合計金額 */
	private Integer totalPrice;
	
	/** 注文日 */
	private Date orderDate;
	
	/** 宛先氏名 */
	private String destinationName;
	
	/** 宛先Eメール */
	private String destinationEmail;
	
	/** 宛先郵便番号 */
	private String destinationZipcode;
	
	/** 宛先住所 */
	private String destinationAddress;
	
	/** 宛先TEL */
	private String destinationTel;
	
	/** 配達時間 */
	private java.sql.Timestamp deliveryTime;
	
	/** 支払方法 */
	private Integer paymentMethod;
	
	/** ユーザ */
	private User user;
	
	/** 注文商品リスト */
	private List<OrderItem> orderItemList;


	public Order() {

	}


	public Order(Integer id, Integer userId, Integer status, Integer totalPrice, Date orderDate, String destinationName,
			String destinationEmail, String destinationZipcode, String destinationAddress, String destinationTel,
			java.sql.Timestamp deliveryTime, Integer paymentMethod, User user, List<OrderItem> orderItemList) {
		this.id = id;
		this.userId = userId;
		this.status = status;
		this.totalPrice = totalPrice;
		this.orderDate = orderDate;
		this.destinationName = destinationName;
		this.destinationEmail = destinationEmail;
		this.destinationZipcode = destinationZipcode;
		this.destinationAddress = destinationAddress;
		this.destinationTel = destinationTel;
		this.deliveryTime = deliveryTime;
		this.paymentMethod = paymentMethod;
		this.user = user;
		this.orderItemList = orderItemList;
	}
	
	/**
	 * ショッピングカート内の消費税を求めます.
	 * 
	 * @return 消費税
	 */
	public int getTax() {
		int subTotal = getCalcPrice();
		return (int)(subTotal * 0.08);
	}


	public int getCalcPrice() {
		int subTotal = 0;
		for (OrderItem orderItem : orderItemList) {
			subTotal += orderItem.getSubTotal();
		}
		return subTotal;
	}

	/**
	 * ショッピングカート内の合計金額(税込)を求めます.
	 * 
	 * @return
	 */
	public int getCalcTotalPrice() {
		return getCalcPrice() + getTax();
	}
	

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}


	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}


	public Integer getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Integer totalPrice) {
		this.totalPrice = totalPrice;
	}


	public Date getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(Date orderDate) {
		this.orderDate = orderDate;
	}


	public String getDestinationName() {
		return destinationName;
	}
	public void setDestinationName(String destinationName) {
		this.destinationName = destinationName;
	}


	public String getDestinationEmail() {
		return destinationEmail;
	}
	public void setDestinationEmail(String destinationEmail) {
		this.destinationEmail = destinationEmail;
	}


	public String getDestinationZipcode() {
		return destinationZipcode;
	}
	public void setDestinationZipcode(String destinationZipcode) {
		this.destinationZipcode = destinationZipcode;
	}


	public String getDestinationAddress() {
		return destinationAddress;
	}
	public void setDestinationAddress(String destinationAddress) {
		this.destinationAddress = destinationAddress;
	}


	public String getDestinationTel() {
		return destinationTel;
	}
	public void setDestinationTel(String destinationTel) {
		this.destinationTel = destinationTel;
	}


	public java.sql.Timestamp getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(java.sql.Timestamp deliveryTime) {
		this.deliveryTime = deliveryTime;
	}


	public Integer getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}


	public List<OrderItem> getOrderItemList() {
		return orderItemList;
	}
	public void setOrderItemList(List<OrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}


	@Override
	public String toString() {
		return "Order [id=" + id + ", userId=" + userId + ", status=" + status + ", totalPrice=" + totalPrice
				+ ", orderDate=" + orderDate + ", destinationName=" + destinationName + ", destinationEmail="
				+ destinationEmail + ", destinationZipcode=" + destinationZipcode + ", destinationAddress="
				+ destinationAddress + ", destinationTel=" + destinationTel + ", deliveryTime=" + deliveryTime
				+ ", paymentMethod=" + paymentMethod + ", user=" + user + ", orderItemList=" + orderItemList + "]";
	}

}

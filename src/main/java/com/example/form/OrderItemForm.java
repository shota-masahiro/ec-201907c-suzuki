package com.example.form;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 注文情報を受け取るフォーム.
 * 
 * @author shota.suzuki
 *
 */
public class OrderItemForm {

	/** 注文ID */
	private String orderId;

	/** 注文日時 */
	private String orderDate;

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

	/** 配達日時 */
	private String deliveryDayTime; //これは入力チェックは入らない

	/** 支払方法 */
	private String paymentMethod;

	/** 合計金額 */
	private String totalPrice;

	/** 配達日 */
	private String deliveryDay;

	/** 配達時間 */
	private String deliveryTime;

	/** ポイント使用の有無 */
	private boolean checkPoint;

	/** 使用ポイント */
	private String usePoint;

	/** クレジットカード番号 */
	private String cardNumber;

	/** 有効期限(月) */
	private String cardExpMonth;

	/** 有効期限(年) */
	private String cardExpYear;

	/** カード名義人 */
	private String cardName;

	/** セキュリティコード */
	private String cardCvv;


	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}


	public String getCardExpMonth() {
		return cardExpMonth;
	}
	public void setCardExpMonth(String cardExpMonth) {
		this.cardExpMonth = cardExpMonth;
	}


	public String getCardExpYear() {
		return cardExpYear;
	}
	public void setCardExpYear(String cardExpYear) {
		this.cardExpYear = cardExpYear;
	}


	public String getCardName() {
		return cardName;
	}
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}


	public String getCardCvv() {
		return cardCvv;
	}
	public void setCardCvv(String cardCvv) {
		this.cardCvv = cardCvv;
	}


	public java.sql.Timestamp getTimestampDeliveryDayTime() {
		String DayTime = deliveryDay + "-" + deliveryTime;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd-HH");
		LocalDateTime date = LocalDateTime.parse(DayTime, formatter);
		java.sql.Timestamp timestamp = java.sql.Timestamp.valueOf(date);
		return timestamp;
	}


	public boolean isCheckPoint() {
		return checkPoint;
	}
	public void setCheckPoint(boolean checkPoint) {
		this.checkPoint = checkPoint;
	}


	public String getDeliveryDayTime() {
		return deliveryDayTime;
	}
	public void setDeliveryDayTime(String deliveryDayTime) {
		this.deliveryDayTime = deliveryDayTime;
	}


	public String getDeliveryDay() {
		return deliveryDay;
	}
	public void setDeliveryDay(String deliveryDay) {
		this.deliveryDay = deliveryDay;
	}


	public Integer getIntTotalPrice() {
		return Integer.parseInt(totalPrice);
	}
	public String getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}


	public Integer getIntOrderId() {
		return Integer.parseInt(orderId);
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String id) {
		this.orderId = id;
	}


	public Date getDateOrderDate() {
		return null;
	};
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
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


	public String getDeliveryTime() {
		return deliveryTime;
	}
	public void setDeliveryTime(String deliveryTime) {
		this.deliveryTime = deliveryTime;
	}


	public Integer getIntePaymentMethod() {
		return Integer.parseInt(paymentMethod);
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}


	public Integer getIntegerUsePoint() {
		return Integer.parseInt(usePoint);
	}
	public String getUsePoint() {
		return usePoint;
	}
	public void setUsePoint(String usePoint) {
		this.usePoint = usePoint;
	}


	@Override
	public String toString() {
		return "OrderItemForm [orderId=" + orderId + ", orderDate=" + orderDate + ", destinationName=" + destinationName
				+ ", destinationEmail=" + destinationEmail + ", destinationZipcode=" + destinationZipcode
				+ ", destinationAddress=" + destinationAddress + ", destinationTel=" + destinationTel
				+ ", deliveryDayTime=" + deliveryDayTime + ", paymentMethod=" + paymentMethod + ", totalPrice="
				+ totalPrice + ", deliveryDay=" + deliveryDay + ", deliveryTime=" + deliveryTime + ", checkPoint="
				+ checkPoint + ", usePoint=" + usePoint + ", cardNumber=" + cardNumber + ", cardExpMonth="
				+ cardExpMonth + ", cardExpYear=" + cardExpYear + ", cardName=" + cardName + ", cardCvv=" + cardCvv
				+ "]";
	}

}
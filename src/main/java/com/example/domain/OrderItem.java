package com.example.domain;

import java.util.List;

/**
 * 注文商品を表すドメイン.
 * 
 * @author shota.suzuki
 *
 */
public class OrderItem {

	/** 注文商品ID */
	private Integer id;

	/** 商品ID */
	private Integer itemId;

	/** 注文ID */
	private Integer orderId;

	/** 数量 */
	private Integer quantity;

	/** サイズ */
	private char size;

	/** 商品 */
	private Item item;

	/** 注文トッピングリスト */
	private List<OrderTopping> orderToppingList;


	public OrderItem() {

	}


	public OrderItem(Integer id, Integer itemId, Integer orderId, Integer quantity, char size, Item item,
			List<OrderTopping> orderToppingList) {
		this.id = id;
		this.itemId = itemId;
		this.orderId = orderId;
		this.quantity = quantity;
		this.size = size;
		this.item = item;
		this.orderToppingList = orderToppingList;
	}


	/**
	 * 商品の小計を求めます.
	 * 
	 * @return 商品小計金額
	 */
	public int getSubTotal() {
		int subTotal = 0;
		for (OrderTopping orderTopping : orderToppingList) {
			Topping topping = orderTopping.getTopping();
			if ('M' == size) {
				subTotal += topping.getPriceM();
			} else {
				subTotal += topping.getPriceL();
			}
		}
		if ('M' == size) {
			subTotal += item.getPriceM() * quantity;
		} else {
			subTotal += item.getPriceL() * quantity;
		}
		return subTotal;
	}

	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}


	public Integer getItemId() {
		return itemId;
	}
	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}


	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}


	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}


	public char getSize() {
		return size;
	}
	public void setSize(char size) {
		this.size = size;
	}


	public Item getItem() {
		return item;
	}
	public void setItem(Item item) {
		this.item = item;
	}


	public List<OrderTopping> getOrderToppingList() {
		return orderToppingList;
	}
	public void setOrderToppingList(List<OrderTopping> orderToppingList) {
		this.orderToppingList = orderToppingList;
	}


	@Override
	public String toString() {
		return "OrderItem [id=" + id + ", itemId=" + itemId + ", orderId=" + orderId + ", quantity=" + quantity
				+ ", size=" + size + ", item=" + item + ", orderToppingList=" + orderToppingList + "]";
	}

}

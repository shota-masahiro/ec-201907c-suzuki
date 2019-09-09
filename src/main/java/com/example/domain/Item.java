package com.example.domain;

import java.util.List;

/**
 * 商品情報を表すドメイン.
 * 
 * @author shota.suzuki
 *
 */
public class Item {
	
	/** 商品ID */
	private Integer id;
	
	/** 商品名 */
	private String name;
	
	/** 商品説明 */
	private String description;
	
	/** Mサイズの価格 */
	private Integer priceM;
	
	/** Lサイズの価格 */
	private Integer priceL;
	
	/** 画像パス */
	private String imagePath;
	
	/** 削除フラグ */
	private boolean deleted;
	
	/** Toppingリスト */
	private List<Topping> toppingList;


	public Item() {

	}


	public Item(Integer id, String name, String description, Integer priceM, Integer priceL, String imagePath,
			boolean deleted, List<Topping> toppingList) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.priceM = priceM;
		this.priceL = priceL;
		this.imagePath = imagePath;
		this.deleted = deleted;
		this.toppingList = toppingList;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}


	public Integer getPriceM() {
		return priceM;
	}
	public void setPriceM(Integer priceM) {
		this.priceM = priceM;
	}


	public Integer getPriceL() {
		return priceL;
	}
	public void setPriceL(Integer priceL) {
		this.priceL = priceL;
	}


	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	public boolean isDeleted() {
		return deleted;
	}
	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}


	public List<Topping> getToppingList() {
		return toppingList;
	}
	public void setToppingList(List<Topping> toppingList) {
		this.toppingList = toppingList;
	}


	@Override
	public String toString() {
		return "Item [id=" + id + ", name=" + name + ", description=" + description + ", priceM=" + priceM + ", priceL="
				+ priceL + ", imagePath=" + imagePath + ", deleted=" + deleted + ", toppingList=" + toppingList + "]";
	}

}

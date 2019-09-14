package com.example.form;

/**
 * レビュー情報を受け取るフォーム.
 * 
 * @author shota.suzuki
 *
 */
public class ReviewForm {

	/** 商品ID */
	private String itemId;

	/** 投稿者 */
	private String name;

	/** 投稿内容 */
	private String content;

	/** 評価点 */
	private String review;


	public String getItemId() {
		return itemId;
	}
	public void setItemId(String itemId) {
		this.itemId = itemId;
	}


	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}


	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}


	public String getReview() {
		return review;
	}
	public void setReview(String review) {
		this.review = review;
	}


	@Override
	public String toString() {
		return "ReviewForm [itemId=" + itemId + ", name=" + name + ", content=" + content + ", review=" + review + "]";
	}

}

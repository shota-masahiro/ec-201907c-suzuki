package com.example.domain;

/**
 * レビュー情報を表すドメイン.
 * 
 * @author shota.suzuki
 *
 */
public class Review {

	/** ID */
	private Integer id;

	/** 商品ID */
	private Integer itemId;

	/** 投稿者 */
	private String name;

	/** 投稿内容 */
	private String content;

	/** レビュー評価(点数) */
	private Integer review;


	public Review() {

	}


	public Review(Integer id, Integer itemId, String name, String content, Integer review) {
		this.id = id;
		this.itemId = itemId;
		this.name = name;
		this.content = content;
		this.review = review;
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


	public Integer getReview() {
		return review;
	}
	public void setReview(Integer review) {
		this.review = review;
	}


	@Override
	public String toString() {
		return "Review [id=" + id + ", itemId=" + itemId + ", name=" + name + ", content=" + content + ", review="
				+ review + "]";
	}

}

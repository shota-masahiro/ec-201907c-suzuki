package com.example.form;

/**
 * 並び替え情報を受け取るフォーム.
 * 
 * @author shota.suzuki
 *
 */
public class ShowItemListForm {

	/** サイズの選択  */
	private String element;

	/** 料金の高低 */
	private String order;

	/** リンクページ */
	private Integer page;


	public String getElement() {
		return element;
	}
	public void setElement(String element) {
		this.element = element;
	}


	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}


	public Integer getPage() {
		return page;
	}
	public void setPage(Integer page) {
		this.page = page;
	}


	@Override
	public String toString() {
		return "ShowItemListForm [element=" + element + ", order=" + order + ", page=" + page + "]";
	}

}
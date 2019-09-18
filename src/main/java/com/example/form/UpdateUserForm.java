package com.example.form;

/**
 * 更新情報を受け取るフォーム.
 * 
 * @author shota.suzuki
 *
 */
public class UpdateUserForm {
	
	/** ユーザID */
	private String id;

	/** メールアドレス */
	private String email;

	/** 郵便番号 */
	private String zipcode;

	/** 住所 */
	private String Address;

	/** 電話番号 */
	private String telephone;

	
	public Integer getIntId() {
		return Integer.parseInt(this.id);
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}


	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


	public String getZipcode() {
		return zipcode;
	}
	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}


	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}


	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}


	@Override
	public String toString() {
		return "UpdateUserForm [id=" + id + ", email=" + email + ", zipcode=" + zipcode + ", Address=" + Address
				+ ", telephone=" + telephone + "]";
	}

}
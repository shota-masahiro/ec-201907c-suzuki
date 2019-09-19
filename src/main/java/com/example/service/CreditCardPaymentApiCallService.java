package com.example.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.domain.RequestCreditPaymentApiDomain;
import com.example.domain.ResponseCreditCardPaymentApiDomain;
import com.example.form.CreditCardPaymentForm;

/**
 * カード決済APIを呼び出すサービスクラス.
 * 
 * @author shota.suzuki
 *
 */
@Service
public class CreditCardPaymentApiCallService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	// 1,内部サーバで動いているWeb-APIのURL
	private static final String URL = "http://localhost:8080/credit-card/payment";

	/**
	 * カード決済WebAPIを呼び出し、レスポンスを返す.
	 * 
	 * @param form クレジットカード情報
	 * @return     WebAPIのレスポンスが入ったドメイン
	 */
	public ResponseCreditCardPaymentApiDomain paymentApiService(CreditCardPaymentForm form) {
		RequestCreditPaymentApiDomain requestCreditPaymentApiDomain = new RequestCreditPaymentApiDomain();
		BeanUtils.copyProperties(form, requestCreditPaymentApiDomain);
		return restTemplate.postForObject(URL, form, ResponseCreditCardPaymentApiDomain.class);
	}

}

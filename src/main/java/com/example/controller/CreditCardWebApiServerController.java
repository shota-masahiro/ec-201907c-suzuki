package com.example.controller;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.RequestCreditCardPaymentApiDomain;
import com.example.domain.ResponseCreditCardPaymentApiDomain;

/**
 * クレジットカード処理を行うWebAPI
 * 
 * @author shota.suzuki
 *
 */
@RestController
@RequestMapping("/credit-card")
public class CreditCardWebApiServerController {


	@RequestMapping(value = "/payment", method = RequestMethod.POST)
	public ResponseCreditCardPaymentApiDomain payment(@RequestBody RequestCreditCardPaymentApiDomain requestCreditCardPaymentApiDomain) {
		ResponseCreditCardPaymentApiDomain responseCreditCardPaymentApiDomain = new ResponseCreditCardPaymentApiDomain();
		int expirationDateYear = 0;
		int expirationDateMonth = 0;
		int cardCvv = 0;
		
		
		System.out.println("サーバー呼び出し成功！");
		
		try {
			//1,有効期限(年)を数値で取得
			expirationDateYear = Integer.parseInt(requestCreditCardPaymentApiDomain.getCard_exp_year());
			//2,有効期限(月)を数値で取得
			expirationDateMonth = Integer.parseInt(requestCreditCardPaymentApiDomain.getCard_exp_month());
			//3,セキュリティコードを数値で取得
			cardCvv = Integer.parseInt(requestCreditCardPaymentApiDomain.getCard_cvv());
		} catch (NumberFormatException e) {
			//1,エラーチェック3.カードの有効期限やセキュリティコードに数値以外の値が渡された場合はエラー
			responseCreditCardPaymentApiDomain.setStatus("error");
			responseCreditCardPaymentApiDomain.setMessage("Error.");
			responseCreditCardPaymentApiDomain.setError_code("E-03");
			return responseCreditCardPaymentApiDomain;
		}

		//1,エラーチェック1.カードの有効期限が実行時年月よりも前だった場合はエラー
		LocalDate endOfTheMonthLocalDate = LocalDate.of(expirationDateYear, expirationDateMonth, 1);
		endOfTheMonthLocalDate = endOfTheMonthLocalDate.with(TemporalAdjusters.lastDayOfMonth());
		if (endOfTheMonthLocalDate.compareTo(LocalDate.now()) < 0) {
			responseCreditCardPaymentApiDomain.setStatus("error");
			responseCreditCardPaymentApiDomain.setMessage("The card is expired.");
			responseCreditCardPaymentApiDomain.setError_code("E-01");
			return responseCreditCardPaymentApiDomain;
		}

		//2,エラーチェック2.セキュリティコードが123でなかった場合はエラー
		if (cardCvv != 123) {
			responseCreditCardPaymentApiDomain.setStatus("error");
			responseCreditCardPaymentApiDomain.setMessage("The card information is incorrect.");
			responseCreditCardPaymentApiDomain.setError_code("E-02");
			return responseCreditCardPaymentApiDomain;
		}

		//1,上記のエラーチェックが問題なければ成功
		responseCreditCardPaymentApiDomain.setStatus("success");
		responseCreditCardPaymentApiDomain.setMessage("OK.");
		responseCreditCardPaymentApiDomain.setError_code("E-00");
		return responseCreditCardPaymentApiDomain;
	}

}
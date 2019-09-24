package com.example.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.domain.ResponseCreditCardPaymentApiDomain;
import com.example.domain.User;
import com.example.enums.PaymentMethod;
import com.example.form.CreditCardPaymentForm;
import com.example.form.OrderItemForm;
import com.example.service.CreditCardPaymentApiCallService;
import com.example.service.ExecuteShoppingCartService;
import com.example.service.RegisterUserService;
import com.example.service.SendingMaiService;

/**
 * 注文確認画面を表示します.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/showOrder")
public class ShowOrderController {

	@Autowired
	private ExecuteShoppingCartService executeShoppingCartService;

	@Autowired
	private RegisterUserService registerUserService;

	@Autowired
	private CreditCardPaymentApiCallService creditCardPaymentApiCallService;

	@Autowired
	private SendingMaiService SendingMaiService;

	@ModelAttribute
	private OrderItemForm setUpOrderItemForm() {
		return new OrderItemForm();
	}

	private final static double calcPoint = 0.05;

	/**
	 * 注文確認画面を表示します.
	 * 
	 * @param orderId 注文ID
	 * @param model   リクエストスコープ
	 * @return        注文確認画面
	 */
	@RequestMapping("")
	public String index(String orderId, @AuthenticationPrincipal LoginUser loginUser, Model model) {

		Order checkOrder = executeShoppingCartService.findByOrderId(Integer.parseInt(orderId));

		if (checkOrder.getOrderItemList().size() == 0) {
			return "forward:/showItemList";
		}

		// 日時のmap
		List<List<Integer>> timeAllList = new ArrayList<>();
		List<Integer> time3List = new ArrayList<>();
		for (int i = 10; i <= 18; i++) {
			time3List.add(i);
			if (i % 3 == 0) {
				timeAllList.add(time3List);
				time3List = new ArrayList<>();
			}
		}
		model.addAttribute("timeAllList", timeAllList);

		// 支払方法のmap
		Map<Integer, String> paymentMap = new LinkedHashMap<>();
		for (PaymentMethod paymentMethod : PaymentMethod.values()) {
			paymentMap.put(paymentMethod.getKey(), paymentMethod.getValue());
		}
		model.addAttribute("paymentMap", paymentMap);

		//有効期限(月)のmap
		Map<String, String> monthMap = new LinkedHashMap<>();
		String month = "";
		for (int i = 1; i <= 12; i++) {
			if (i < 10) {
				month = "0" + i;
			} else {
				month = String.valueOf(i);
			}
			monthMap.put(month, month);
		}
		model.addAttribute("monthMap", monthMap);

		//有効期限(年)のyear
		Map<Integer, Integer> yearMap = new LinkedHashMap<>();
		for (int i = 2018; i <= 2038; i++) {
			yearMap.put(i, i);
		}
		model.addAttribute("yearMap", yearMap);

		User user = registerUserService.load(loginUser.getUser().getId());
		model.addAttribute("user", user);

		Order order = executeShoppingCartService.findByOrderId(Integer.parseInt(orderId));
		model.addAttribute("order", order);

		return "order_confirm";
	}


	/**
	 * 注文完了処理を行います.
	 * 
	 * @param form  リクエストパラメータ
	 * @param model リクエストスコープ
	 * @return      注文完了画面
	 */
	@RequestMapping("/toOrder")
	public String toOrder(
			OrderItemForm form,
			Model model,
			@AuthenticationPrincipal LoginUser loginUser) {

		//クレジットカード決済処理
		if ("2".equals(form.getPaymentMethod())) {

			CreditCardPaymentForm creditCardPaymentForm = createCreditCardPaymentForm(form, loginUser);
			ResponseCreditCardPaymentApiDomain responseCreditCardPaymentApiDomain = creditCardPaymentApiCallService.paymentApiService(creditCardPaymentForm);

			//エラーチェック
			if ("error".equals(responseCreditCardPaymentApiDomain.getStatus())) {
				return index(form.getOrderId(), loginUser, model);
			}
		}

		//送信メールの準備
		SendingMaiService.run(form);

		//User情報の取得
		Order order = executeShoppingCartService.findByOrderId(form.getIntOrderId());
		User user = registerUserService.load(order.getUserId());

		//ポイント使用のエラーチェック
		if (form.getUsePoint() != "") {
			if (form.getIntegerUsePoint() > user.getPoint()) {
				return index(form.getOrderId(), loginUser, model);
			}
		}

		//付与するポイントの計算処理
		int newPoint = (int)(order.getCalcPrice() * calcPoint);

		//ポイントを使用したときの処理
		if (form.isCheckPoint()) {
			//ポイント使用はユーザのポイントを減算する
			user.setPoint(user.getPoint() - form.getIntegerUsePoint());
			form.setTotalPrice(String.valueOf(form.getIntTotalPrice() - form.getIntegerUsePoint()));
		} else {
			//ポイントを使用しない場合はポイントを加算する
			user.setPoint(user.getPoint() + newPoint);
		}

		registerUserService.updatePoint(user);
		executeShoppingCartService.update(form);

		return "order_finished";
	}


	/**
	 * クレジットカード情報をDomainに移し替えます.
	 * 
	 * @param form      リクエストパラメータ
	 * @param loginUser ログインユーザ情報
	 * @return          クレジットカード情報
	 */
	private CreditCardPaymentForm createCreditCardPaymentForm(OrderItemForm form, LoginUser loginUser) {
		CreditCardPaymentForm creditCardPaymentForm = new CreditCardPaymentForm();
		creditCardPaymentForm.setUser_id(String.valueOf(loginUser.getUser().getId()));
		creditCardPaymentForm.setOrder_number(form.getOrderId());
		creditCardPaymentForm.setAmount(form.getTotalPrice());
		creditCardPaymentForm.setPaymentMethod(form.getPaymentMethod());
		creditCardPaymentForm.setCard_number(form.getCardNumber());
		creditCardPaymentForm.setCard_exp_year(form.getCardExpYear());
		creditCardPaymentForm.setCard_exp_month(form.getCardExpMonth());
		creditCardPaymentForm.setCard_name(form.getCardName());
		creditCardPaymentForm.setCard_cvv(form.getCardCvv());
		return creditCardPaymentForm;
	}

}
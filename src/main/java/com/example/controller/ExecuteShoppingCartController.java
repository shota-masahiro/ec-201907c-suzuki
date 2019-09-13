package com.example.controller;

import java.util.Random;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.Order;
import com.example.form.ExecuteShoppingCartForm;
import com.example.service.ExecuteShoppingCartService;

/**
 * ショッピングカートを操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/executeShoppingCart")
public class ExecuteShoppingCartController {

	@Autowired
	private ExecuteShoppingCartService executeShoppingCartService;

	@Autowired
	private HttpSession session;

	@ModelAttribute
	public ExecuteShoppingCartForm setUpExecuteShoppingCartForm() {
		return new ExecuteShoppingCartForm();
	}


	/**
	 * ショッピングカート画面を出力します.
	 * 
	 * @param orderId 注文ID
	 * @param model   リクエストスコープ
	 * @return        ショッピングカート画面
	 */
	public String index(Integer orderId, Model model) {
		if (orderId == null || orderId == 0) {
			return "cart_list";
		}
		Order order = executeShoppingCartService.findByOrderId(orderId);
		model.addAttribute("order", order);
		return "cart_list";
	}


	/**
	 * ショッピングカート画面を出力します.
	 * 
	 * @param loginUser ユーザ情報
	 * @param model     リクエストスコープ
	 * @return          ショッピングカート画面
	 */
	@RequestMapping("/header")
	public String header(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer preOrderId = (Integer) session.getAttribute("preOrderId");
		Integer preUserId = (Integer) session.getAttribute("preUserId");
		Order order = null;

		if (loginUser == null) { //未ログイン時の処理
			order = executeShoppingCartService.findByUserId2(preUserId);
		} else { //ログイン時の処理

			if (preUserId != null) { //未ログイン時にカートに入れた商品を上書きする処理
				executeShoppingCartService.update2(preOrderId, loginUser.getUser().getId());
			}
			order = executeShoppingCartService.findByUserId2(loginUser.getUser().getId());
		}

		model.addAttribute("order", order);
		return "cart_list";
	}


	/**
	 * ショッピングカートに商品を入れる追加する処理
	 * 
	 * @param form  リクエストパラメータ
	 * @param model リクエストスコープ
	 * @return      ショッピングカート画面
	 */
	@RequestMapping("/toInCart")
	public String toInCart(ExecuteShoppingCartForm form, @AuthenticationPrincipal LoginUser loginUser, Model model) {
		Integer preOrderId = (Integer) session.getAttribute("preOrderId");
		Integer preUserId = (Integer) session.getAttribute("preUserId");
		Order order = null;

		if (loginUser == null) { //未ログイン時の処理

			if (preOrderId == null) { //カートに商品を入れる1回目の処理
				Random rnd = new Random();
				Integer token = rnd.nextInt(10000);
				form.setUserId(String.valueOf(token));
			} else { //カートに商品を入れる2回目の処理
				form.setUserId(String.valueOf(preUserId));
			}

			order = executeShoppingCartService.insert(form);
			session.setAttribute("preOrderId", order.getId());
			session.setAttribute("preUserId", order.getUserId());
		} else { //ログイン時の処理

			if (preUserId != null) { //未ログイン時にカートに入れた商品を上書きする処理
				executeShoppingCartService.update2(preOrderId, loginUser.getUser().getId());
			}
			form.setUserId(String.valueOf(loginUser.getUser().getId()));
			order = executeShoppingCartService.insert(form);
		}

		model.addAttribute("order", order);
		return "cart_list";
	}


	/**
	 * 削除処理をします.
	 * 
	 * @param orderId     注文ID
	 * @param orderItemId 注文商品ID
	 * @param model       リクエストパラメータ
	 * @return            ショッピングカート画面
	 */
	@RequestMapping("/delete")
	public String delete(String orderId, String orderItemId, Model model) {
		executeShoppingCartService.delete(Integer.parseInt(orderItemId));
		return index(Integer.parseInt(orderId), model);
	}

}
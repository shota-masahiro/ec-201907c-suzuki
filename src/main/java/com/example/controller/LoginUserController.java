package com.example.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.LoginUserForm;
import com.example.service.RegisterUserService;

/**
 * ログイン画面を操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/loginUser")
public class LoginUserController {
	
	@Autowired
	private HttpSession session;

	@Autowired
	private RegisterUserService registerUserService;

	@ModelAttribute
	public LoginUserForm setUpLoginUserForm() {
		return new LoginUserForm();
	}


	/**
	 * ログイン画面を出力します.
	 * 
	 * @return ログイン画面
	 */
	@RequestMapping("")
	public String toLogin(Model model, String errorMessage) {
		if (errorMessage != null) {
			model.addAttribute("errorMessage", errorMessage);
		}
		return "login";
	}


	/**
	 * ログイン処理をします.
	 * 
	 * @return 商品一覧画面
	 */
	@RequestMapping("/login")
	public String login(
			@Validated LoginUserForm form,
			BindingResult result,
			Model model) {

		String errorMessage = null;
		
		System.out.println(form);

		if (result.hasErrors()) {
			return toLogin(model, errorMessage);
		}

		User user = registerUserService.findByEmailAndPassword(form);
		if (user == null) {
			errorMessage = "メールまたはパスワードが不正です";
			return toLogin(model, errorMessage);
		}
		
		session.setAttribute("user", user);

		return "test";
	}

}
package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.User;
import com.example.form.RegisterUserForm;
import com.example.service.RegisterUserService;

/**
 * ユーザ情報を操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/register")
public class RegisterUserController {

	@Autowired
	private RegisterUserService registerUserService;


	@ModelAttribute
	public RegisterUserForm setUpRegisterUserForm() {
		return new RegisterUserForm();
	}


	/**
	 * ユーザ登録画面を出力します.
	 * 
	 * @return ユーザ登録画面
	 */
	@RequestMapping("")
	public String toRegister() {
		return "register_user";
	}


	/**
	 * user情報を挿入します.
	 * 
	 * @param form リクエストパラメータ
	 * @param result 入力チェック
	 * @param model リクエストパラメータ
	 * @return ログイン画面
	 */
	@RequestMapping("/registerUser")
	public String register(
			@Validated RegisterUserForm form,
			BindingResult result,
			Model model) {

		User checkUser = registerUserService.findByEmail(form);
		if (checkUser != null) {
			result.rejectValue("email", "", "そのメールアドレスは既に登録されています");
		}

		if (!form.getPassword().equals(form.getConfirmationPassword())) {
			result.rejectValue("password", "", "パスワードが一致していません");
		}

		if (result.hasErrors()) {
			return toRegister();
		}

		User user = new User();
		BeanUtils.copyProperties(form, user);

		registerUserService.insert(user);
		return "login";
	}

}

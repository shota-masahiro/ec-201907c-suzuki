package com.example.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.LoginUser;
import com.example.domain.User;
import com.example.form.UpdateUserForm;
import com.example.service.RegisterUserService;

/**
 * マイページを操作するコントローラ
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/maypage")
public class MaypageController {

	@Autowired
	private RegisterUserService registerUserService;

	@ModelAttribute
	public UpdateUserForm setUpUpdateUserForm() {
		return new UpdateUserForm();
	}


	/**
	 * マイページ画面を出力します.
	 * 
	 * @return マイページ画面
	 */
	@RequestMapping("")
	public String index() {
		return "maypage";
	}


	/**
	 * ポイント残高画面を出力します.
	 * 
	 * @param loginUser ログインユーザ情報
	 * @param model     リクエストスコープ
	 * @return          ポイント残高画面
	 */
	@RequestMapping("/user_point")
	public String point(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		User user = registerUserService.load(loginUser.getUser().getId());
		model.addAttribute("point", user.getPoint());
		return "user_point";
	}


	/**
	 * 登録変更画面を出力します.
	 * 
	 * @param loginUser ログインユーザ情報
	 * @param model     リクエストスコープ
	 * @return          登録変更画面
	 */
	@RequestMapping("/toUpdate")
	public String toUpdate(@AuthenticationPrincipal LoginUser loginUser, Model model) {
		User user = registerUserService.load(loginUser.getUser().getId());
		model.addAttribute("user", user);
		return "update_user";
	}


	/**
	 * 更新処理をします.
	 * 
	 * @param form リクエストパラメータ
	 * @return     マイページ画面
	 */
	@RequestMapping("/update_user")
	public String update(UpdateUserForm form, @AuthenticationPrincipal LoginUser loginUser) {
		User user = new User();
		BeanUtils.copyProperties(form, user);
		user.setId(loginUser.getUser().getId());
		registerUserService.updateUser(user);
		return index();
	}

}
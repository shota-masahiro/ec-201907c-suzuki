package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.SecurityConfig;
import com.example.domain.User;
import com.example.form.LoginUserForm;
import com.example.form.RegisterUserForm;
import com.example.repository.UserRepository;

/**
 * user情報を操作するサービス,
 * 
 * @author shota.suzuki
 *
 */
@Service
@Transactional
public class RegisterUserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private SecurityConfig securityConfig;

	private static String secret = "1234";
	private static String salt = "5678";


	/**
	 * user情報の挿入をします.
	 * 
	 * @param user userオブジェクト
	 */
	public void insert(User user) {
		user.setAddress(securityConfig.encryptText(secret, salt, user.getAddress()));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setPoint(0);
		userRepository.insert(user);
	}


	/**
	 * user情報を取得します.
	 * 
	 * @param id ユーザID
	 * @return   userオブジェクト
	 */
	public User load(Integer id) {
		User user = userRepository.load(id);
		user.setAddress(securityConfig.decryptText(secret, salt, user.getAddress()));
		return user;
	}


	/**
	 * user情報を取得します.
	 * 
	 * @param email メールアドレス
	 * @return      userオブジェクト
	 */
	public User findByEmail(RegisterUserForm form) {
		return userRepository.findByEmail(form.getEmail());
	}


	/**
	 * user情報を取得します.
	 * 
	 * @param form リクエストパラメータ
	 * @return     userオブジェクト
	 */
	public User findByEmailAndPassword(LoginUserForm form) {
		User user = userRepository.findByEmailAndPassword(form.getEmail(), form.getPassword());
		user.setAddress(securityConfig.decryptText(secret, salt, user.getAddress()));
		return user;
	}


	/**
	 * 更新処理をします.
	 * 
	 * @param user userオブジェクト
	 */
	public void updatePoint(User user) {
		userRepository.updatePoint(user);
	}


	/**
	 * 更新処理をします.
	 * 
	 * @param user userオブジェクト
	 */
	public void updateUser(User user) {
		user.setAddress(securityConfig.encryptText(secret, salt, user.getAddress()));
		userRepository.updateUser(user);
	}

}
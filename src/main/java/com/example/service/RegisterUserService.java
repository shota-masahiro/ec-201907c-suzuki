package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	/**
	 * user情報の挿入をします.
	 * 
	 * @param user userオブジェクト
	 */
	public void insert(User user) {
		userRepository.insert(user);
	}
	
	
	/**
	 * user情報を取得します.
	 * 
	 * @param id ユーザID
	 * @return   userオブジェクト
	 */
	public User load(Integer id) {
		return userRepository.load(id);
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
		return userRepository.findByEmailAndPassword(form.getEmail(), form.getPassword());
	}

}
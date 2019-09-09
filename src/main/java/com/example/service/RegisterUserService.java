package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.User;
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
	 * @param email メールアドレス
	 * @return userオブジェクト
	 */
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}

}
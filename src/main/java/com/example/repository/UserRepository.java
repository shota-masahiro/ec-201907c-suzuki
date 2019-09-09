package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.User;

/**
 * usersテーブルを操作するリポジトリ.
 * 
 * @author shota.suzuki
 *
 */
@Repository
public class UserRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;


	/**
	 * userオブジェクトを生成するローマッパー,
	 */
	private static final RowMapper<User> USER_ROW_MAPPER = (rs, i) -> {
		User user = new User();
		user.setId(rs.getInt("id"));
		user.setName(rs.getString("name"));
		user.setEmail(rs.getString("email"));
		user.setPassword(rs.getString("password"));
		user.setZipcode(rs.getString("zipcode"));
		user.setAddress(rs.getString("address"));
		user.setTelephone(rs.getString("telephone"));
		return user;
	};


	/**
	 * user情報を挿入します.
	 * 
	 * @param user userオブジェクト
	 */
	public void insert(User user) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO users(name, email, password, zipcode, address, telephone) ");
		sql.append("VALUES(:name, :email, :password, :zipcode, :address, :telephone);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(user);
		template.update(sql.toString(), param);
	}


	/**
	 * user情報を取得します.
	 * 
	 * @param email メールアドレス
	 * @return userオブジェクト
	 */
	public User findByEmail(String email) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, email, password, zipcode, address, telephone ");
		sql.append("FROM users WHERE email=:email;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email);
		List<User> userList = template.query(sql.toString(), param, USER_ROW_MAPPER);
		if (userList.size() != 0) {
			return userList.get(0);
		} else {
			return null;
		}
	}


	/**
	 * user情報を取得します.
	 * 
	 * @param email メールアドレス
	 * @param password パスワード
	 * @return
	 */
	public User findByEmailAndPassword(String email, String password) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, email, password, zipcode, address, telephone ");
		sql.append("FROM users WHERE email=:email AND password=:password;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("email", email).addValue("password", password);
		List<User> userList = template.query(sql.toString(), param, USER_ROW_MAPPER);
		if (userList.size() != 0) {
			return userList.get(0);
		} else {
			return null;
		}
	}

}

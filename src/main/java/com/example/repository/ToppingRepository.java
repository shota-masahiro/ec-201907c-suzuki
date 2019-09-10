package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.example.domain.Topping;

@Repository
public class ToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * トッピングオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Topping> TOPPING_ROW_MAPPER = (rs, i) -> {
		Topping topping = new Topping();
		topping.setId(rs.getInt("id"));
		topping.setName(rs.getString("name"));
		topping.setPriceM(rs.getInt("price_m"));
		topping.setPriceL(rs.getInt("price_l"));
		return topping;
	};


	/**
	 * トッピング情報を全件取得します.
	 * 
	 * @return トッピング情報一覧
	 */
	public List<Topping> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, price_m, price_l ");
		sql.append("FROM toppings ORDER BY id");
		List<Topping> toppingList = template.query(sql.toString(), TOPPING_ROW_MAPPER);
		return toppingList;
	}

}

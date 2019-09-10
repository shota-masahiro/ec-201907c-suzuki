package com.example.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.OrderTopping;

/**
 * order_toppingsテーブルを操作するリポジトリ.
 * 
 * @author shota.suzuki
 *
 */
@Repository
public class OrderToppingRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;


	/**
	 * OrderToppingオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<OrderTopping> ORDER_TOPPING_ROW_MAPPER = (rs, i) -> {
		OrderTopping orderTopping = new OrderTopping();
		orderTopping.setId(rs.getInt("id"));
		orderTopping.setToppingId(rs.getInt("topping_id"));
		orderTopping.setOrderItemId(rs.getInt("order_item_id"));
		return orderTopping;
	};


	/**
	 * 挿入処理を行います.
	 * 
	 * @param orderTopping OrderToppingオブジェクト
	 */
	public void insert(OrderTopping orderTopping) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO order_toppings(topping_id, order_item_id) ");
		sql.append("VALUES(:toppingId, :orderItemId)");
		SqlParameterSource param = new BeanPropertySqlParameterSource(orderTopping);
		template.update(sql.toString(), param);
	}


}

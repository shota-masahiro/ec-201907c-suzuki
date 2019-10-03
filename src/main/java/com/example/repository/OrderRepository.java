package com.example.repository;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;
import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.domain.OrderTopping;
import com.example.domain.Topping;

/**
 * ordersテーブルを操作するリポジトリ.
 * 
 * @author shota.suzuki
 *
 */
@Repository
public class OrderRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	private SimpleJdbcInsert insert;

	@PostConstruct
	public void init() {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert((JdbcTemplate)template.getJdbcOperations());
		SimpleJdbcInsert withTableName = simpleJdbcInsert.withTableName("orders");
		insert = withTableName.usingGeneratedKeyColumns("id");
	}


	/**
	 * 注文情報を取得するリザルトセットエクストラクター.
	 */
	@SuppressWarnings("unused")
	private static final ResultSetExtractor<Order> ORDER_RESULT_SET_EXTRACTOR = (rs) -> {
		Order order = new Order();

		List<OrderItem> orderItemList = new ArrayList<>();
		order.setOrderItemList(orderItemList);
		List<OrderTopping> orderToppingList = null;

		int preOrderId = -1;
		int preOrderItemId = -1;
		int preOrderToppingId = -1;

		while (rs.next()) {

			Integer orderId = rs.getInt("o1_id");
			if (preOrderId != orderId || orderId != null) {
				order.setId(rs.getInt("o1_id"));
				order.setUserId(rs.getInt("o1_user_id"));
				order.setStatus(rs.getInt("o1_status"));
				order.setTotalPrice(rs.getInt("o1_total_price"));
				order.setOrderDate(rs.getDate("o1_order_date"));
				order.setDestinationName(rs.getString("o1_destination_name"));
				order.setDestinationEmail(rs.getString("o1_destination_email"));
				order.setDestinationZipcode(rs.getString("o1_destination_zipcode"));
				order.setDestinationAddress(rs.getString("o1_destination_address"));
				order.setDestinationTel(rs.getString("o1_destination_tel"));
				order.setDeliveryTime(rs.getTimestamp("o1_delivery_time"));
				order.setPaymentMethod(rs.getInt("o1_payment_method"));
			}
			preOrderId = orderId;

			int orderItemId = rs.getInt("o2_id");
			if (orderItemId != preOrderItemId && orderItemId != 0) {
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("o2_id"));
				orderItem.setItemId(rs.getInt("o2_item_id"));
				orderItem.setOrderId(rs.getInt("o2_order_id"));
				orderItem.setQuantity(rs.getInt("o2_quantity"));
				String ch = rs.getString("o2_size");
				orderItem.setSize(ch.charAt(0));

				Item item = new Item();
				item.setId(rs.getInt("i_id"));
				item.setName(rs.getString("i_name"));
				item.setDescription(rs.getString("i_description"));
				item.setPriceM(rs.getInt("i_price_m"));
				item.setPriceL(rs.getInt("i_price_l"));
				item.setImagePath(rs.getString("i_image_path"));
				item.setDeleted(rs.getBoolean("i_deleted"));

				orderItem.setItem(item);
				orderToppingList = new ArrayList<>();
				orderItem.setOrderToppingList(orderToppingList);
				orderItemList.add(orderItem);
			}
			preOrderItemId = orderItemId;

			int orderToppingId = rs.getInt("o3_id");
			if (orderToppingId != preOrderToppingId && orderToppingId != 0) {
				OrderTopping orderTopping = new OrderTopping();
				orderTopping.setId(rs.getInt("o3_id"));
				orderTopping.setToppingId(rs.getInt("o3_topping_id"));
				orderTopping.setOrderItemId(rs.getInt("o3_order_item_id"));

				Topping topping = new Topping();
				topping.setId(rs.getInt("t_id"));
				topping.setName(rs.getString("t_name"));
				topping.setPriceM(rs.getInt("t_price_m"));
				topping.setPriceL(rs.getInt("t_price_l"));
				orderTopping.setTopping(topping);
				orderToppingList.add(orderTopping);
			}
			preOrderToppingId = orderToppingId;
		}
		return order;
	};


	/**
	 * orderオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Order> ORDER_ROW_MAPPER = (rs, i) -> {
		Order order = new Order();
		order.setId(rs.getInt("id"));
		order.setUserId(rs.getInt("user_id"));
		order.setStatus(rs.getInt("status"));
		order.setTotalPrice(rs.getInt("total_price"));
		order.setOrderDate(rs.getDate("order_date"));
		order.setDestinationName(rs.getString("destination_name"));
		order.setDestinationEmail(rs.getString("destination_email"));
		order.setDestinationZipcode(rs.getString("destination_zipcode"));
		order.setDestinationAddress(rs.getString("destination_address"));
		order.setDestinationTel(rs.getString("destination_tel"));
		order.setDeliveryTime(rs.getTimestamp("delivery_time"));
		order.setPaymentMethod(rs.getInt("payment_method"));
		return order;
	};


	/**
	 * 注文情報を取得します.
	 * 
	 * @param userId ユーザID
	 * @param status 注文状態
	 * @return       注文情報
	 */
	public Order findByUserIdAndStatus(Integer userId, Integer status) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, user_id, status, total_price, order_date, destination_name, destination_email, destination_zipcode, destination_address, destination_tel, delivery_time, payment_method ");
		sql.append("FROM orders WHERE user_id=:userId AND status=:status");
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId).addValue("status", status);
		List<Order> orderList = template.query(sql.toString(), param, ORDER_ROW_MAPPER);
		if (orderList.size() != 0) {
			return orderList.get(0);
		} else {
			return null;
		}
	}


	/**
	 * 注文情報を取得します.
	 * 
	 * @param id 注文ID
	 * @return   注文情報
	 */
	public Order findByOrderId(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT o1.id o1_id, o1.user_id o1_user_id, o1.status o1_status, o1.total_price o1_total_price, o1.order_date o1_order_date, o1.destination_name o1_destination_name, o1.destination_email o1_destination_email, o1.destination_zipcode o1_destination_zipcode, o1.destination_address o1_destination_address, o1.destination_tel o1_destination_tel, o1.delivery_time o1_delivery_time, o1.payment_method o1_payment_method,");
		sql.append("o2.id o2_id, o2.item_id o2_item_id, o2.order_id o2_order_id, o2.quantity o2_quantity, o2.size o2_size,");
		sql.append("o3.id o3_id, o3.topping_id o3_topping_id, o3.order_item_id o3_order_item_id,");
		sql.append("i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, i.image_path i_image_path, i.deleted i_deleted,");
		sql.append("t.id t_id, t.name t_name, t.price_m t_price_m, t.price_l t_price_l ");
		sql.append("FROM orders o1 FULL OUTER JOIN order_items o2 ON o1.id=o2.order_id ");
		sql.append("FULL OUTER JOIN order_toppings o3 ON o2.id=o3.order_item_id ");
		sql.append("FULL OUTER JOIN items i ON o2.item_id=i.id ");
		sql.append("FULL OUTER JOIN toppings t ON o3.topping_id=t.id ");
		sql.append("WHERE o1.id=:id");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		Order order = template.query(sql.toString(), param, ORDER_RESULT_SET_EXTRACTOR);
		return order;
	}


	/**
	 * 注文情報を取得します.
	 * 
	 * @param userId ユーザID
	 * @return       注文情報
	 */
	public Order findByUserId(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT o1.id o1_id, o1.user_id o1_user_id, o1.status o1_status, o1.total_price o1_total_price, o1.order_date o1_order_date, o1.destination_name o1_destination_name, o1.destination_email o1_destination_email, o1.destination_zipcode o1_destination_zipcode, o1.destination_address o1_destination_address, o1.destination_tel o1_destination_tel, o1.delivery_time o1_delivery_time, o1.payment_method o1_payment_method,");
		sql.append("o2.id o2_id, o2.item_id o2_item_id, o2.order_id o2_order_id, o2.quantity o2_quantity, o2.size o2_size,");
		sql.append("o3.id o3_id, o3.topping_id o3_topping_id, o3.order_item_id o3_order_item_id,");
		sql.append("i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, i.image_path i_image_path, i.deleted i_deleted,");
		sql.append("t.id t_id, t.name t_name, t.price_m t_price_m, t.price_l t_price_l ");
		sql.append("FROM orders o1 FULL OUTER JOIN order_items o2 ON o1.id=o2.order_id ");
		sql.append("FULL OUTER JOIN order_toppings o3 ON o2.id=o3.order_item_id ");
		sql.append("FULL OUTER JOIN items i ON o2.item_id=i.id ");
		sql.append("FULL OUTER JOIN toppings t ON o3.topping_id=t.id ");
		sql.append("WHERE o1.user_id=:userId AND status=2 OR status=3;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		Order order = template.query(sql.toString(), param, ORDER_RESULT_SET_EXTRACTOR);
		return order;
	}


	/**
	 * 注文情報を取得します.
	 * 
	 * @param userId 注文ID
	 * @return       注文情報
	 */
	public Order findByUserId2(Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT o1.id o1_id, o1.user_id o1_user_id, o1.status o1_status, o1.total_price o1_total_price, o1.order_date o1_order_date, o1.destination_name o1_destination_name, o1.destination_email o1_destination_email, o1.destination_zipcode o1_destination_zipcode, o1.destination_address o1_destination_address, o1.destination_tel o1_destination_tel, o1.delivery_time o1_delivery_time, o1.payment_method o1_payment_method,");
		sql.append("o2.id o2_id, o2.item_id o2_item_id, o2.order_id o2_order_id, o2.quantity o2_quantity, o2.size o2_size,");
		sql.append("o3.id o3_id, o3.topping_id o3_topping_id, o3.order_item_id o3_order_item_id,");
		sql.append("i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, i.image_path i_image_path, i.deleted i_deleted,");
		sql.append("t.id t_id, t.name t_name, t.price_m t_price_m, t.price_l t_price_l ");
		sql.append("FROM orders o1 FULL OUTER JOIN order_items o2 ON o1.id=o2.order_id ");
		sql.append("FULL OUTER JOIN order_toppings o3 ON o2.id=o3.order_item_id ");
		sql.append("FULL OUTER JOIN items i ON o2.item_id=i.id ");
		sql.append("FULL OUTER JOIN toppings t ON o3.topping_id=t.id ");
		sql.append("WHERE o1.user_id=:userId and status=0;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("userId", userId);
		Order order = template.query(sql.toString(), param, ORDER_RESULT_SET_EXTRACTOR);
		return order;
	}


	/**
	 * 挿入処理をします.
	 * 
	 * @param order orderオブジェクト
	 * @return      注文ID
	 */
	public Integer insert(Order order) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO orders(user_id, status, total_price) ");
		sql.append("VALUES(:userId, :status, :totalPrice);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		Number key = insert.executeAndReturnKey(param);
		return key.intValue();
	}


	/**
	 * 更新処理をします.
	 * 
	 * @param order orderオブジェクト
	 */
	public void update(Order order) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE orders ");
		sql.append("SET status=:status, order_date=:orderDate, total_price=:totalPrice, destination_name=:destinationName, destination_email=:destinationEmail, destination_zipcode=:destinationZipcode, destination_address=:destinationAddress, destination_tel=:destinationTel, delivery_time=:deliveryTime, payment_method=:paymentMethod, order_number=:orderNumber ");
		sql.append("WHERE id=:id;");
		SqlParameterSource param = new BeanPropertySqlParameterSource(order);
		template.update(sql.toString(), param);
	}



	/**
	 * 更新処理をします.
	 * 
	 * @param userId    ユーザID
	 * @param preUserId tokenID
	 */
	public void update2(Integer orderId, Integer userId) {
		StringBuilder sql = new StringBuilder();
		sql.append("UPDATE orders ");
		sql.append("SET user_id=:userId ");
		sql.append("WHERE id=:orderId;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("orderId", orderId).addValue("userId", userId);
		template.update(sql.toString(), param);
	}
	
	
	/**
	 * 注文番号を取得します.
	 * 
	 * @return 注文番号
	 */
	public String getOrderNumber() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT to_char(nextval('customer_code_seq'), 'FM00000');");
		SqlParameterSource param = new MapSqlParameterSource();
		return template.queryForObject(sql.toString(), param, String.class);
	}

}
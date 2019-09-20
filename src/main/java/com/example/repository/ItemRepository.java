package com.example.repository;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Item;

/**
 * itemsテーブルを操作するリポジトリ.
 * 
 * @author shota.suzuki
 *
 */
@Repository
public class ItemRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;

	/**
	 * Itemオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Item> ITEM_ROW_MAPPER = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("id"));
		item.setName(rs.getString("name"));
		item.setDescription(rs.getString("description"));
		item.setPriceM(rs.getInt("price_m"));
		item.setPriceL(rs.getInt("price_l"));
		item.setImagePath(rs.getString("image_path"));
		item.setDeleted(rs.getBoolean("deleted"));
		return item;
	};
	
	
	/**
	 * Itemオブジェクトを生成するテーブル結合版ローマッパー.
	 */
	private static final RowMapper<Item> ITEM_ROW_MAPPER2 = (rs, i) -> {
		Item item = new Item();
		item.setId(rs.getInt("i_id"));
		item.setName(rs.getString("i_name"));
		item.setDescription(rs.getString("i_description"));
		item.setPriceM(rs.getInt("i_price_m"));
		item.setPriceL(rs.getInt("i_price_l"));
		item.setImagePath(rs.getString("i_image_path"));
		item.setDeleted(rs.getBoolean("i_deleted"));
		return item;
	};


	/**
	 * 商品情報一覧を取得します.
	 * 
	 * @return 商品情報一覧
	 */
	public List<Item> findAll() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, description, price_m, price_l, image_path, deleted ");
		sql.append("FROM items ORDER BY id;");
		List<Item> itemList = template.query(sql.toString(), ITEM_ROW_MAPPER);
		return itemList;
	}


	/**
	 * 商品情報一覧を取得します.
	 * 
	 * @param searchName 検索単語
	 * @return           商品情報一覧
	 */
	public List<Item> findBySearchName(String searchName) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, description, price_m, price_l, image_path, deleted ");
		sql.append("FROM items WHERE name LIKE :name ORDER BY id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("name", searchName);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_ROW_MAPPER);
		return itemList;
	}


	/**
	 * 商品詳細情報を取得します.
	 * 
	 * @param id 商品ID
	 * @return   商品詳細情報
	 */
	public Item load(Integer id) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, description, price_m, price_l, image_path, deleted ");
		sql.append("FROM items WHERE id=:id;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("id", id);
		List<Item> itemList = template.query(sql.toString(), param, ITEM_ROW_MAPPER);
		if (itemList.size() != 0) {
			return itemList.get(0);
		} else {
			return null;
		}
	}


	/**
	 * 商品情報一覧を取得します.
	 * 
	 * @param size  ピザのサイズ
	 * @param price 料金の高低
	 * @return      商品情報一覧
	 */
	public List<Item> findByPrice(String size, String price) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, name, description, price_m, price_l, image_path, deleted ");
		sql.append("FROM items ORDER BY ");
		sql.append(" "+ size +" ");
		sql.append(price +";");
		List<Item> itemList = template.query(sql.toString(), ITEM_ROW_MAPPER);
		return itemList;
	}
	
	
	/**
	 * 人気商品の上位5件を取得します.
	 * 
	 * @return 商品情報一覧
	 */
	public List<Item> findByRanking() {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT i.id i_id, i.name i_name, i.description i_description, i.price_m i_price_m, i.price_l i_price_l, i.image_path i_image_path, i.deleted i_deleted, ");
		sql.append("count (*) AS count ");
		sql.append("FROM orders o1 FULL OUTER JOIN order_items o2 ON o1.id=o2.order_id ");
		sql.append("FULL OUTER JOIN items i ON o2.item_id=i.id ");
		sql.append("where status=2 or status=3 ");
		sql.append("GROUP BY i.id , i.name , i.description, i.price_m, i.price_l , i.image_path , i.deleted ");
		sql.append("ORDER BY count DESC LIMIT 5;");
		List<Item> orderList = template.query(sql.toString(), ITEM_ROW_MAPPER2);
		return orderList;
	}

}
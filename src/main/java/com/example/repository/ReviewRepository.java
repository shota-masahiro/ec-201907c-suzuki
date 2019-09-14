package com.example.repository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import com.example.domain.Review;

/**
 * reviewテーブルを操作するリポジトリ.
 * 
 * @author shota.suzuki
 *
 */
@Repository
public class ReviewRepository {

	@Autowired
	private NamedParameterJdbcTemplate template;


	/**
	 * Reviewオブジェクトを生成するローマッパー.
	 */
	private static final RowMapper<Review> REVIEW_ROW_MAPPER = (rs, i) -> {
		Review review = new Review();
		review.setId(rs.getInt("id"));
		review.setItemId(rs.getInt("item_id"));
		review.setName(rs.getString("name"));
		review.setContent(rs.getString("content"));
		review.setReview(rs.getInt("review"));
		return review;
	};


	/**
	 * レビュー情報を取得します.
	 * 
	 * @param itemId 商品ID
	 * @return       レビュー情報一覧
	 */
	public List<Review> findByItemId(Integer itemId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT id, item_id, name, content, review ");
		sql.append("FROM reviews WHERE item_id=:itemId;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);
		List<Review> reviewList = template.query(sql.toString(), param, REVIEW_ROW_MAPPER);
		return reviewList;
	}


	/**
	 * 挿入処理をします.
	 * 
	 * @param review reviewオブジェクト
	 */
	public void insert(Review review) {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO reviews(item_id, name, content, review) ");
		sql.append("values(:itemId, :name, :content, :review);");
		SqlParameterSource param = new BeanPropertySqlParameterSource(review);
		template.update(sql.toString(), param);
	}


	/**
	 * 商品の平均評価を取得します.
	 * 
	 * @param itemId 商品ID
	 * @return       平均評価
	 */
	public Integer averageReview(Integer itemId) {
		StringBuilder sql = new StringBuilder();
		sql.append("SELECT avg(review) ");
		sql.append("FROM reviews WHERE item_id=:itemId;");
		SqlParameterSource param = new MapSqlParameterSource().addValue("itemId", itemId);
		return (Integer)template.queryForObject(sql.toString(), param, Integer.class);
	}

}

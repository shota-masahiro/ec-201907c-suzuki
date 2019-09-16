package com.example.service;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Review;
import com.example.form.ReviewForm;
import com.example.repository.ReviewRepository;

/**
 * レビュー情報を操作するサービス.
 * 
 * @author shota.suzuki
 * 
 */
@Service
@Transactional
public class ExecuteReviewService {

	@Autowired
	private ReviewRepository reviewRepository;


	/**
	 * レビュー情報を取得します.
	 * 
	 * @param itemId 商品ID
	 * @return       レビュー情報一覧
	 */
	public List<Review> findByItemId(Integer itemId) {
		return reviewRepository.findByItemId(itemId);
	}
	
	
	/**
	 * 挿入処理をします.
	 * 
	 * @param form リクエストパラメータ
	 */
	public void insert(ReviewForm form) {
		Review review = new Review();
		BeanUtils.copyProperties(form, review);
		review.setItemId(form.getIntItemId());
		review.setReview(form.getIntReview());
		reviewRepository.insert(review);
	}
	
	
	/**
	 * 商品の平均評価を取得します.
	 * 
	 * @param itemId 商品ID
	 * @return       平均評価
	 */
	public String averageReview(Integer itemId) {
		Integer avgReview = reviewRepository.averageReview(itemId);
		StringBuilder star = new StringBuilder("");
		if (avgReview != null) {
			for (int i = 1; i <= avgReview; i++) {
				star.append("★");
			}
		}
		return star.toString();
	}


}

package com.example.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.Review;
import com.example.form.ReviewForm;
import com.example.service.ExecuteReviewService;
import com.example.service.ShowItemDetailService;

/**
 * レビュー画面を操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/review")
public class ExecuteReviewController {

	@Autowired
	private ExecuteReviewService executeReviewService;

	@Autowired
	private ShowItemDetailService showItemDetailService;

	@ModelAttribute
	public ReviewForm setUpReviewForm() {
		return new ReviewForm();
	}


	/**
	 * レビュー一覧画面を出力します.
	 * 
	 * @param itemId 商品ID
	 * @param model  リクエストスコープ
	 * @return       商品レビュー画面
	 */
	@RequestMapping("")
	public String index(String itemId, Model model) {
		Item item = showItemDetailService.showDetail(itemId);
		model.addAttribute(item);
		List<Review> reviewList = executeReviewService.findByItemId(Integer.parseInt(itemId));
		model.addAttribute("reviewList", reviewList);
		return "test";
	}


}

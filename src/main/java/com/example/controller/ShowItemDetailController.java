package com.example.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.domain.Topping;
import com.example.service.ExecuteReviewService;
import com.example.service.ShowItemDetailService;

/**
 * 商品詳細情報を操作するコントローラ.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/detail")
public class ShowItemDetailController {

	@Autowired
	private ShowItemDetailService showItemDetailService;
	
	@Autowired
	private ExecuteReviewService executeReviewService;

	/**
	 * 商品詳細情報を出力します.
	 * 
	 * @param itemId 商品ID
	 * @param model  リクエストスコープ
	 * @return       商品詳細画面
	 */
	@RequestMapping("/showDetail")
	public String index(String itemId, Model model) {
		Item item = showItemDetailService.showDetail(itemId);
		model.addAttribute("item", item);

		int j = 1;
		List<Map<Integer, String>> toppingAllList = new ArrayList<>();
		Map<Integer, String> toppingMap = new LinkedHashMap<>();
		for (Topping topping : item.getToppingList()) {
			toppingMap.put(topping.getId(), topping.getName());
			if (j % 3 == 0) {
				toppingAllList.add(toppingMap);
				toppingMap = new LinkedHashMap<>();
			}
			j++;
		}
		toppingAllList.add(toppingMap);
		model.addAttribute("toppingAllList", toppingAllList);

		Map<Integer, Integer> quantityMap = new LinkedHashMap<>();
		for (int i = 1; i <= 12; i++) {
			quantityMap.put(i, i);
		}
		model.addAttribute("quantityMap", quantityMap);
		
		String star = executeReviewService.averageReview(item.getId());
		model.addAttribute("star", star);
		
		Map<Integer, Integer> reviewMap = new LinkedHashMap<>();
		for (int i = 1; i <= 5; i++) {
			reviewMap.put(i, i);
		}
		model.addAttribute("reviewMap", reviewMap);

		return "item_detail";
	}

}

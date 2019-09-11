package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.service.ShowItemListService;

/**
 * 商品一覧画面を表示するコントローラー.
 * 
 * @author shota.suzuki
 *
 */
@Controller
@RequestMapping("/itemList")
public class ShowItemListController {

	@Autowired
	private ShowItemListService showItemListService;

	private static final int VIEW_SIZE = 9;

	/**
	 * 商品一覧画面を出力します.
	 * 
	 * @return 商品一覧画面
	 */
	@RequestMapping("")
	public String index(String searchName, Model model) {
		
		List<Item> itemList = showItemListService.findAll(searchName);
		List<List<Item>> itemAllList = showItemListService.createItemList(itemList);
		
		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(itemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);
		
		model.addAttribute("itemAllList", itemAllList);

		return "item_list";
	}


	/**
	 * ページングのリンクを作成するメソッド.
	 * 
	 * @param model    リクエストスコープ
	 * @param itemPage ページング情報
	 * @return
	 */
	private List<Integer> calcPageNumbers(Model model, Page<Item> itemPage) {
		int totalPages = itemPage.getTotalPages();
		List<Integer> pageNumbers = null;
		if (totalPages > 0) {
			pageNumbers = new ArrayList<>();
			for (int i = 1; i <= totalPages; i++) {
				pageNumbers.add(i);
			}
		}
		return pageNumbers;
	}

}

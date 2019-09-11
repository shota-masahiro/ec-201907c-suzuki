package com.example.controller;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.domain.Item;
import com.example.form.ShowItemListForm;
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
	
	@ModelAttribute
	public ShowItemListForm setUpShowItemListForm() {
		return new ShowItemListForm();
	}

	private static final int VIEW_SIZE = 9;

	/**
	 * 商品一覧画面を出力します.
	 * 
	 * @return 商品一覧画面
	 */
	@RequestMapping("")
	public String index(ShowItemListForm form, String searchName, Model model) {
		
		List<Item> itemList = null;
		List<List<Item>> itemAllList = null;
		
		//商品検索
		if (searchName != null) {
			itemList = showItemListService.findAll(searchName);
		}
		
		//並び替え
		if (form.getElement() != null) {
			System.out.println(form);
			itemList = showItemListService.findByPrice(form);
		}
		
		//トップ画面表示
		if (form.getElement() == null && searchName == null) {
			itemList = showItemListService.findAll(searchName);
		}
		
		itemAllList = showItemListService.createItemList(itemList);
		model.addAttribute("itemAllList", itemAllList);
		
//		List<Item> itemList = showItemListService.findAll(searchName);
//		List<List<Item>> itemAllList = showItemListService.createItemList(itemList);
		
		//オートコンプリート
		List<Item> autoItemList = showItemListService.findAll("");
		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(autoItemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);
		
		
		Map<String, String> selectSizeMap = new LinkedHashMap<>();
		selectSizeMap.put("", "---");
		selectSizeMap.put("price_m", "Mサイズ");
		selectSizeMap.put("price_l", "Lサイズ");
		model.addAttribute("selectSizeMap", selectSizeMap);
		
		Map<String, String> sortPriceMap = new LinkedHashMap<>();
		sortPriceMap.put("", "---");
		sortPriceMap.put("asc", "料金の低い順");
		sortPriceMap.put("desc", "料金の高い順");
		model.addAttribute("sortPriceMap", sortPriceMap);

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

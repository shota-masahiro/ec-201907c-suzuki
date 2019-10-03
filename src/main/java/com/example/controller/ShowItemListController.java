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
@RequestMapping("/")
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

		//ページング指定
		if (form.getPage() == null) {
			form.setPage(1);
		}

		//並び替え
		if (form.getElement() != null && !"".equals(form.getElement())) {
			System.out.println("並び替え処理:"+form);
			itemList = showItemListService.findByPrice(form);
		}

		//トップ画面表示 to 商品検索
		if (form.getElement() == null && searchName == null || searchName != null) {
			itemList = showItemListService.findAll(searchName);
		}

		//itemListがnullだったら全件検索
		if (itemList == null) {
			itemList = showItemListService.findAll(searchName);
		}

		//商品検索・並び替えした後もページングできるようにスコープに格納
		model.addAttribute("name", searchName);
		model.addAttribute("element", form.getElement());
		model.addAttribute("order", form.getOrder());

		//オートコンプリート
		List<Item> autoItemList = showItemListService.findAll("");
		StringBuilder itemListForAutocomplete = showItemListService.getItemListForAutocomplete(autoItemList);
		model.addAttribute("itemListForAutocomplete", itemListForAutocomplete);

		//ページング処理
		Page<Item> itemPage = showItemListService.showListPaging(form.getPage(), VIEW_SIZE, itemList);
		List<Item> itemPageList = itemPage.getContent();
		List<List<Item>> itemAllList = showItemListService.createItemList(itemPageList);
		model.addAttribute("itemAllList", itemAllList);

		//ページングのリンクを作成
		List<Integer> pageNumbers = calcPageNumbers(model, itemPage);
		model.addAttribute("itemPage", itemPage);
		model.addAttribute("pageNumbers", pageNumbers);

		//並び替えの値をmapにset
		Map<String, String> selectSizeMap = new LinkedHashMap<>();
		selectSizeMap.put("", "---");
		selectSizeMap.put("price_m", "Mサイズ");
		selectSizeMap.put("price_l", "Lサイズ");
		model.addAttribute("selectSizeMap", selectSizeMap);

		//並び替えの値をmapにset
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
	 * @return         1ページ分の商品情報
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
	
	
	/**
	 * 人気ランキング画面を出力します.
	 * 
	 * @param model リクエストスコープ
	 * @return      人気ランキング画面
	 */
	@RequestMapping("/ranking")
	public String showItemListByRanking(Model model) {
		List<Item> itemList = showItemListService.findByRanking();
		model.addAttribute("itemList", itemList);
		System.out.println(itemList);
		return "item_rank";
	}

}
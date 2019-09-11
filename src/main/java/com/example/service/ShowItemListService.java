package com.example.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.form.ShowItemListForm;
import com.example.repository.ItemRepository;

/**
 * 商品一覧情報を操作するサービス.
 * 
 * @author shota.suzuki
 *
 */
@Service
@Transactional
public class ShowItemListService {

	@Autowired
	private ItemRepository itemRepository;

	/**
	 * 商品一覧情報を取得します.
	 * 
	 * @return 商品一覧情報
	 */
	public List<Item> findAll(String searchName) {
		List<Item> itemList;
		if (searchName == null || "".equals(searchName)) {
			itemList = itemRepository.findAll();
		} else {
			itemList = itemRepository.findBySearchName("%" + searchName + "%");
		}
		return itemList;
	}
	
	
	/**
	 * 商品情報一覧を取得します.
	 * 
	 * @param form リクエストパラメータ
	 * @return     商品情報一覧
	 */
	public List<Item> findByPrice(ShowItemListForm form) {
		return itemRepository.findByPrice(form.getElement(), form.getOrder());
	}


	/**
	 * itemListに入れ替えるメソッド.
	 * 
	 * @param itemList 商品一覧情報
	 * @return 商品一覧情報
	 */
	public List<List<Item>> createItemList(List<Item> itemPageList) {
		List<Item> item3List = new ArrayList<>();
		List<List<Item>> itemAllList = new ArrayList<>();
		
		for (int i = 1; i <= itemPageList.size(); i++) {
			item3List.add(itemPageList.get(i - 1));
			if (i % 3 == 0) {
				itemAllList.add(item3List);
				item3List = new ArrayList<>();
			}
		}
		itemAllList.add(item3List);
		return itemAllList;
	}


	/**
	 * ページング用メソッド.
	 * 
	 * @param page 表示させたいページ数
	 * @param size 1ページに表示させる従業員数
	 * @param itemList 絞り込み対象リスト
	 * @return 1ページに表示されるサイズ分の商品情報
	 */
	public Page<Item> showListPaging(int page, int size, List<Item> itemList) {
		page--;
		int startItemCount = page * size;
		List<Item> list;
		if (itemList.size() < startItemCount) {
			list = Collections.emptyList();
		} else {
			int toIndex = Math.min(startItemCount + size, itemList.size());
			list = itemList.subList(startItemCount, toIndex);
		}
		Page<Item> itemPage = new PageImpl<Item>(list, PageRequest.of(page, size), itemList.size());
		return itemPage;
	}


	/**
	 * オートコンプリート用の文字列を生成するメソッド.
	 * 
	 * @param itemList 商品情報
	 * @return         商品名情報
	 */
	public StringBuilder getItemListForAutocomplete(List<Item> itemList) {
		StringBuilder itemListForAutocomplete = new StringBuilder();
		
		for (int i = 0; i < itemList.size(); i++) {
			if (i != 0) {
				itemListForAutocomplete.append(",");
			}
			Item item = itemList.get(i);
			itemListForAutocomplete.append("\"");
			itemListForAutocomplete.append(item.getName());
			itemListForAutocomplete.append("\"");
		}
		return itemListForAutocomplete;
	}

}

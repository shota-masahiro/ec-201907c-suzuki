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
	public List<Item> findAll() {
		List<Item> itemList = itemRepository.findAll();
		return itemList;
	}
	
	
	/**
	 * itemListに入れ替えるメソッド.
	 * 
	 * @param itemList 商品一覧情報
	 * @return 商品一覧情報
	 */
	public List<List<Item>> createItemList(List<Item> itemList) {
		List<Item> item3List = new ArrayList<>();
		List<List<Item>> itemAllList = new ArrayList<>();
		int i = 1;
		for (Item item : itemList) {
			item3List.add(item);
			if (i % 3 == 0) {
				itemAllList.add(item3List);
				item3List = new ArrayList<>();
			}
			i++;
		}
		if (item3List.size() != 0) {
			itemAllList.add(item3List);
		}
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











}

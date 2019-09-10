package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.domain.Item;
import com.example.repository.ItemRepository;
import com.example.repository.ToppingRepository;

/**
 * 商品詳細情報を操作するサービス.
 * 
 * @author shota.suzuki
 *
 */
@Service
@Transactional
public class ShowItemDetailService {
	
	@Autowired
	private ItemRepository itemRepository;
	
	@Autowired
	private ToppingRepository toppingRepository;
	
	
	/**
	 * 商品詳細情報を取得します.
	 * 
	 * @param id 商品ID
	 * @return 商品詳細情報
	 */
	public Item showDetail(String id) {
		Item item = itemRepository.load(Integer.parseInt(id));
		item.setToppingList(toppingRepository.findAll());
		return item;
	}
	
	
}

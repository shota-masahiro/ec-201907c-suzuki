package com.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
		return itemRepository.findAll();
	}

}

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
import com.example.service.ShowItemDetailService;

@Controller
@RequestMapping("/detail")
public class ShowItemDetailController {

	@Autowired
	private ShowItemDetailService showItemDetailService;

	@RequestMapping("/showDetail")
	public String index(String id, Model model) {
		Item item = showItemDetailService.showDetail(id);
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

		return "item_detail";
	}
























}

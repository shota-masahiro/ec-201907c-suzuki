package com.example;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.repository.OrderRepository;

@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	private OrderRepository orderRepository;


	@RequestMapping("")
	public String index() {

		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String preOrderNum = sdf.format(date);

		StringBuilder orderNumber = new StringBuilder(preOrderNum);
		orderNumber.append(orderRepository.getOrderNumber());

		System.out.println(orderNumber);

		return "test";
	}

}
package com.example.controller;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.Order;
import com.example.domain.OrderItem;
import com.example.repository.OrderRepository;

@RestController
@RequestMapping("/test")
public class CsvDownloadController {

	@Autowired
	private OrderRepository orderRepository;


	@RequestMapping("")
	public Map<Integer, Integer> index(String orderId) {
		Order order = orderRepository.findByOrderId(Integer.parseInt(orderId));
		try {
			FileWriter fw = new FileWriter("C:\\env\\springworkspace\\ec-201907c-suzuki\\test.csv", false);
			PrintWriter pw = new PrintWriter(new BufferedWriter(fw));

			for (OrderItem orderItem : order.getOrderItemList()) {
				pw.print(order.getOrderNumber());
				pw.print(",");
				pw.print(order.getDestinationName());
				pw.print(",");
				pw.print(order.getDestinationEmail());
				pw.print(",");
				pw.print(order.getDestinationZipcode());
				pw.print(",");
				pw.print(order.getDestinationAddress());
				pw.print(",");
				pw.print(order.getDestinationTel());
				pw.print(",");
				pw.print(orderItem.getItem().getName());
				pw.print(",");
				if (orderItem.getSize() == 'M') {
					pw.print(orderItem.getItem().getPriceM());
				} else {
					pw.print(orderItem.getItem().getPriceL());
				}
				pw.print(",");
				pw.print(orderItem.getQuantity());
				pw.print(",");
				pw.print(orderItem.getSubTotal());
				pw.print(",");
				pw.print(order.getTotalPrice());
				pw.print(",");
				pw.print(order.getStatus());
				
				pw.println();
			}
			pw.close();
			System.out.println("CSVの出力が完了しました。");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<Integer, Integer> likeMap = new HashMap<>();
		likeMap.put(1, 1);
		return likeMap;
	}

}
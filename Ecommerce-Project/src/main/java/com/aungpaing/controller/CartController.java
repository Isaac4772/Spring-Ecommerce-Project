package com.aungpaing.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import com.aungpaing.controller.request.OrderProductData;
import com.aungpaing.controller.request.OrderReceiverData;
import com.aungpaing.controller.request.OrderRequestData;
import com.aungpaing.model.entity.OrderItem;
import com.aungpaing.model.entity.OrderStatus;
import com.aungpaing.model.entity.Orders;
import com.aungpaing.model.service.OrderService;
import com.aungpaing.model.service.ProductService;

@Controller
public class CartController {

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@GetMapping("/cart/details")
	public String home() {
		return "cart";
	}

	@GetMapping("/cart/checkout")
	public String checkoutPage(ModelMap map) {
		map.put("name", "Aung Aung");
		map.put("phone", "0912345567");
		map.put("email", "aungaung@gmail.com");
		map.put("address", "Yangon");
		return "checkout";
	}

	@PostMapping("/cart/place-order")
	public @ResponseBody String makeOrder(@RequestBody OrderRequestData obj) {

		try {
			OrderReceiverData receiver = obj.getReceiver();
			List<OrderProductData> itemList = obj.getOrderItems();

			// create new order
			Orders newOrder = new Orders();
			newOrder.setStatus(OrderStatus.pending);
			newOrder.setShippingAddress(receiver.getAddress());
			newOrder.setShippingEmail(receiver.getEmail());
			newOrder.setShippingName(receiver.getName());
			newOrder.setShippingPhone(receiver.getPhone());
			newOrder.setCustomer(null);

			// add order items
			for (var item : itemList) {
				var product = productService.findById(item.getProductId());

				var orderItem = new OrderItem();
				orderItem.setProduct(product);
				orderItem.setQuantity(item.getQty());
				newOrder.addOrderItem(orderItem);
			}

			// save order to db
			Orders savedOrder = orderService.save(newOrder);
			return savedOrder.getId() + "";
		} catch (Exception e) {
			return "";
		}
	}

}

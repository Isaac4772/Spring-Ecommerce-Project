package com.aungpaing.controller;

import java.security.Principal;
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
import com.aungpaing.model.entity.Order;
import com.aungpaing.model.entity.User;
import com.aungpaing.model.service.OrderService;
import com.aungpaing.model.service.ProductService;
import com.aungpaing.model.service.UserService;

@Controller
public class CartController {

	@Autowired
	private ProductService productService;

	@Autowired
	private OrderService orderService;

	@Autowired
	private UserService userService;

	@GetMapping("/cart/details")
	public String home() {
		return "cart";
	}

	@GetMapping("/cart/checkout")
	public String checkoutPage(ModelMap map, Principal principal) {
		User loginUser = userService.profile(principal.getName());
		map.put("name", loginUser.getName());
		map.put("phone", loginUser.getPhone());
		map.put("email", loginUser.getEmail());
		map.put("address", loginUser.getAddress());
		return "checkout";
	}

	@PostMapping("/cart/place-order")
	public @ResponseBody String makeOrder(@RequestBody OrderRequestData obj, Principal principal) {

		try {
			OrderReceiverData receiver = obj.getReceiver();
			List<OrderProductData> itemList = obj.getOrderItems();

			// create new order
			Order newOrder = new Order();
			newOrder.setStatus(OrderStatus.pending);
			newOrder.setShippingAddress(receiver.getAddress());
			newOrder.setShippingEmail(receiver.getEmail());
			newOrder.setShippingName(receiver.getName());
			newOrder.setShippingPhone(receiver.getPhone());
			newOrder.setCustomer(userService.profile(principal.getName()));

			// add order items
			for (var item : itemList) {
				var product = productService.findById(item.getProductId());

				var orderItem = new OrderItem();
				orderItem.setProduct(product);
				orderItem.setQuantity(item.getQty());
				newOrder.addOrderItem(orderItem);
			}

			// save order to db
			Order savedOrder = orderService.save(newOrder);
			return savedOrder.getId() + "";
		} catch (Exception e) {
			return "";
		}
	}

}

package com.daniel.apps.ecommerce.app.util;

import com.daniel.apps.ecommerce.app.model.Category;
import com.daniel.apps.ecommerce.app.model.Order;
import com.daniel.apps.ecommerce.app.model.OrderItem;
import com.daniel.apps.ecommerce.app.model.Product;

import java.math.BigDecimal;
import java.util.*;

public class MailUtil {

    public static Map<String, Object> sendCreateAccountEmailPayload(
                                                                    String to,
                                                                    String username,
                                                                    String veryMailLink) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("subject", "Created Account");
        payload.put("to", to);
        payload.put("title", "Account Created Successfully");
        payload.put("username", username);
        payload.put("message", "<p>Your account has been created successfully. Please activate your account using the button below.</p>");
        payload.put("activationUrl", veryMailLink);
        payload.put("buttonText", "Activate Account");
        payload.put("footerText", "If you didn’t sign up, you can safely ignore this email.");
        return payload;
    }

    public static Map<String, Object> sendOrderPurchaseEmailPayload(Order order, String to,  String username) {
        Set<OrderItem> orderItems = order.getItems();
        List<Map<String, Object>> itemList = orderItems.stream()
                .map(orderItem -> {
                    Product product = orderItem.getProduct();
                    var cat = Optional.ofNullable(product.getCategory());
                    Map<String, Object> map = new HashMap<>();
                    map.put("name", product.getName());
                    map.put("qty", orderItem.getQuantity());
                    map.put("price", product.getPrice());
                    map.put("totalPrice", order.getTotal());
                    map.put("category", cat.orElse(new Category()));
                    map.put("paymentMethod", orderItem.getOrder().getPayment().getPaymentMethod());
                    map.put("paymentStatus", orderItem.getOrder().getPayment().getPaymentStatus());
                    return map;
                })
                .toList();

        BigDecimal total = itemList.stream()
                .map(item -> (BigDecimal) item.get("totalPrice"))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Map<String, Object> payload = new HashMap<>();
        payload.put("subject", "Order Confirmation from Liberta");
        payload.put("to", to);
        payload.put("title", "Thanks for your purchase!");
        payload.put("username", username);
        payload.put("message", "<p>Your order has been confirmed. Below is your receipt:</p>");
        payload.put("items", itemList);
        payload.put("total", total);
//        payload.put("buttonUrl", "https://example.com/orders/12345");
//        payload.put("buttonText", "View Your Order");
        payload.put("footerText", "Need help? Respond to this email");

        return payload;
    }

    public static Map<String, Object> contactPayload(
            String to,
            String username) {
        Map<String, Object> payload = new HashMap<>();
        payload.put("subject", "Thank you for contacting our team");
        payload.put("to", to);
        payload.put("title", "");
        payload.put("username", username);
        payload.put("message", "<p> Thank you for contacting us. Our team " +
                "will respond to you shortly!</p>");
        payload.put("footerText", "If you did’t contact, you can safely " +
                "ignore this email.");
        return payload;
    }

}

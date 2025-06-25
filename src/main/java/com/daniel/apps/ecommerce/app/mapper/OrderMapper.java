package com.daniel.apps.ecommerce.app.mapper;


import com.daniel.apps.ecommerce.app.dto.order.OrderItemDto;
import com.daniel.apps.ecommerce.app.dto.product.ProductBoughtDto;
import com.daniel.apps.ecommerce.app.model.Category;
import com.daniel.apps.ecommerce.app.model.Order;
import com.daniel.apps.ecommerce.app.model.OrderItem;
import com.daniel.apps.ecommerce.app.model.Product;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

@Component
public class OrderMapper {


    public Collection<OrderItemDto> toOrderItemDto(Order order) {
        Collection<OrderItemDto> orderItemDos = new ArrayList<>();
            for (OrderItem orderItem : order.getItems()) {
                Product product = orderItem.getProduct();
                var cat = Optional.ofNullable(product.getCategory());
                OrderItemDto orderItemDto = new OrderItemDto(new ProductBoughtDto(product.getId()
                        , product.getName(), product.getDescription(),
                        orderItem.getQuantity(), order.getTotal(),
                        product.getPrice(),
                        cat.orElse(new Category()),
                        order.getPayment()));
                orderItemDos.add(orderItemDto);
            }


        return orderItemDos;
    }


}

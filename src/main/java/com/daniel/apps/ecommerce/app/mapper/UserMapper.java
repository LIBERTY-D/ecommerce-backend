package com.daniel.apps.ecommerce.app.mapper;

import com.daniel.apps.ecommerce.app.dto.order.OrderItemDto;
import com.daniel.apps.ecommerce.app.dto.product.ProductBoughtDto;
import com.daniel.apps.ecommerce.app.dto.user.UserResponseDto;
import com.daniel.apps.ecommerce.app.exception.NoSuchUserException;
import com.daniel.apps.ecommerce.app.model.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class UserMapper {
    public  UserResponseDto touserResponseDto(User user){

        if(user==null){
            throw  new NoSuchUserException("user cannot be null");
        }
        List<OrderItemDto> orderItems = new ArrayList<>();
        for (Order o : user.getOrders()) {
            for (OrderItem i : o.getItems()) {
                Product product = i.getProduct();
                Category cat = product.getCategory();
                if(cat==null){
                    cat = new Category();
                }
                orderItems.add(new OrderItemDto(new ProductBoughtDto(
                        product.getId(),
                        product.getName(),
                        product.getDescription(),
                        i.getQuantity(),
                        o.getTotal(),
                        product.getPrice(),
                         cat,
                        o.getPayment()

                )));
            }
        }

        return new UserResponseDto(user.getId(),
                user.getFirstName(),user.getLastName(),user.getEmail(),
                user.isEnabled(),user.isNonLocked(),user.getRoles(),
                user.getAddress(), user.getCreatedAt(),user.getUpdatedAt(),
                orderItems);

    }
}

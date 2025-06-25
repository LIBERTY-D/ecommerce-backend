package com.daniel.apps.ecommerce.app.controller.imp;

import com.daniel.apps.ecommerce.app.controller.Controller;
import com.daniel.apps.ecommerce.app.dto.order.OrderDto;
import com.daniel.apps.ecommerce.app.dto.order.OrderItemDto;
import com.daniel.apps.ecommerce.app.http.HttpResponse;
import com.daniel.apps.ecommerce.app.service.imp.OrderServiceImp;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Collection;



@RestController
@RequiredArgsConstructor

public class OrderController extends BaseController implements Controller<OrderDto, OrderItemDto,Long> {
    private  final OrderServiceImp orderServiceImp;
    @GetMapping("orders")
    @Override
    public ResponseEntity<HttpResponse<Collection<OrderItemDto>>> findAll() {
        Collection<OrderItemDto> orders= orderServiceImp.findAllOrders();
        HttpResponse<Collection<OrderItemDto>> response =
                HttpResponse.<Collection<OrderItemDto>>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("Orders retrieved successfully")
                        .data(orders).build();

        return ResponseEntity.ok(response);
    }

    @Override
    @GetMapping("orders/{id}")
    public ResponseEntity<HttpResponse<OrderItemDto>> findOne(Long id) {

        HttpResponse<OrderItemDto> response =
                HttpResponse.<OrderItemDto>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("OrderItemDto retrieved successfully")
                        .results(orderServiceImp.findOrderById(id)).build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("orders/{id}")
    @Override
    public ResponseEntity<HttpResponse<OrderItemDto>> deleteOne(Long id) {
        orderServiceImp.deleteOrderById(id);
        HttpResponse<OrderItemDto> response =
                HttpResponse.<OrderItemDto>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .message("OrderItemDto deleted successfully").build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("orders/{id}")
    @Override

    public ResponseEntity<HttpResponse<OrderItemDto>> updateOne(Long id, OrderDto updatedEntity) {

//        OrderItemDto user =  orderServiceImp.updateOrder(id,updatedEntity);
//        HttpResponse<OrderItemDto> response =
//                HttpResponse.<OrderItemDto>builder()
//                        .timeStamp(LocalDateTime.now())
//                        .status(HttpStatus.OK)
//                        .statusCode(HttpStatus.OK.value()).data(user)
//                        .message("OrderItemDto updated successfully").build();
//        return ResponseEntity.ok(response);

        return  null;
    }


    @PostMapping("orders")
    @Override
    public ResponseEntity<HttpResponse<OrderItemDto>> createOne(OrderDto newEntity) {
        Collection<OrderItemDto> orders = orderServiceImp.createOrder(newEntity);
        HttpResponse<OrderItemDto> response =
                HttpResponse.<OrderItemDto>builder()
                        .timeStamp(LocalDateTime.now())
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value()).results(orders)
                        .message("Orders created successfully").build();
        return ResponseEntity.ok(response);
    }
}


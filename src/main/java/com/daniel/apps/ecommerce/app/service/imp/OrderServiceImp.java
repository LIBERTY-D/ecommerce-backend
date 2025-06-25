package com.daniel.apps.ecommerce.app.service.imp;


import com.daniel.apps.ecommerce.app.dto.order.CartItem;
import com.daniel.apps.ecommerce.app.dto.order.OrderDto;
import com.daniel.apps.ecommerce.app.dto.order.OrderItemDto;
import com.daniel.apps.ecommerce.app.exception.NoSuchOrderException;
import com.daniel.apps.ecommerce.app.exception.ProductQuantityExceededException;
import com.daniel.apps.ecommerce.app.mapper.OrderMapper;
import com.daniel.apps.ecommerce.app.model.*;
import com.daniel.apps.ecommerce.app.model.enums.OrderStatus;
import com.daniel.apps.ecommerce.app.model.enums.PaymentStatus;
import com.daniel.apps.ecommerce.app.repository.OrderRepository;
import com.daniel.apps.ecommerce.app.service.email.EmailService;
import com.daniel.apps.ecommerce.app.util.MailUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderServiceImp {


    private final OrderRepository orderRepository;
    private final ProductServiceImp productServiceImp;
    private final UserServiceImp userServiceImp;
    private  final OrderMapper orderMapper;
    private final EmailService emailService;

    public Collection<OrderItemDto> findAllOrders() {

        var orders=  this.orderRepository.findAll();
         return orders.stream().flatMap(order -> orderMapper.toOrderItemDto(order).stream()).collect(Collectors.toList());

    }

    public Collection<OrderItemDto> findOrderById(Long id) {
        Order order= this.findById(id);
        return  orderMapper.toOrderItemDto(order);
    }


    public void deleteOrderById(Long id) {
        Order order = findById(id);
        orderRepository.delete(order);

    }

    @Transactional
    public Collection<OrderItemDto> createOrder(OrderDto orderDto) {
        User user = userServiceImp.findUserById(orderDto.userId());
        var cartItems = orderDto.cartItems();


        if (cartItems.isEmpty()) {
            throw new NoSuchOrderException("Order items are required.");
        }

        BigDecimal totalPrice = BigDecimal.ZERO;
        Order order = new Order();
        order.setStatus(OrderStatus.PENDING);
        order.setUser(user);

        for (CartItem cartItem : cartItems) {
            Product product = productServiceImp.findProductById(cartItem.productId());

            if (product.getQuantity() >= cartItem.quantity()) {
                BigDecimal itemTotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.quantity()));
                totalPrice = totalPrice.add(itemTotal);

                OrderItem orderItem = new OrderItem();
                orderItem.setProduct(product);
                orderItem.setQuantity(cartItem.quantity());
                orderItem.setPrice(itemTotal);

                order.addOrderItem(orderItem);
                product.setQuantity(product.getQuantity() - cartItem.quantity());

            } else {
                throw new ProductQuantityExceededException("Not enough stock for: " + product.getName());
            }
        }

        order.setTotal(totalPrice);

        Payment payment = new Payment();
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentStatus(PaymentStatus.COMPLETED);
        payment.setPaymentMethod(orderDto.payment().paymentMethod());
        payment.setAmount(totalPrice);
        payment.setOrder(order);

        order.setPayment(payment);

        Order createdOrder = orderRepository.save(order);

        //send email
       emailService.sendEmail(MailUtil.sendOrderPurchaseEmailPayload(createdOrder,user.getEmail(),user.getFirstName()),
               "purchase-confirmation-email.html");
        return orderMapper.toOrderItemDto(createdOrder);
    }


    private Order findById(Long id) {
        Optional<Order> orderOpt = orderRepository.findById(id);
        if (orderOpt.isEmpty()) {
            throw new NoSuchOrderException("no order with such id");
        }
        return orderOpt.get();
    }


}

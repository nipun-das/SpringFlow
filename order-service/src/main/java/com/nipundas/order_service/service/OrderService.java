package com.nipundas.order_service.service;

import com.nipundas.order_service.client.InventoryClient;
import com.nipundas.order_service.dto.OrderRequest;
import com.nipundas.order_service.model.Order;
import com.nipundas.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;

    public void placeOrder(OrderRequest orderRequest){

        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(),orderRequest.quantity());

        if(isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());

            orderRepository.save(order);
        }
        else{
            throw new RuntimeException("Product with skucode : "+ orderRequest.skuCode()+" is not in stock");
        }
    }
}

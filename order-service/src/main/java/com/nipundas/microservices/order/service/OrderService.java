package com.nipundas.microservices.service;

import com.nipundas.microservices.client.InventoryClient;
import com.nipundas.microservices.dto.OrderRequest;
import com.nipundas.microservices.event.OrderPlacedEvent;
import com.nipundas.microservices.model.Order;
import com.nipundas.microservices.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    public void placeOrder(OrderRequest orderRequest){

        var isProductInStock = inventoryClient.isInStock(orderRequest.skuCode(),orderRequest.quantity());

        if(isProductInStock) {
            Order order = new Order();
            order.setOrderNumber(UUID.randomUUID().toString());
            order.setPrice(orderRequest.price());
            order.setSkuCode(orderRequest.skuCode());
            order.setQuantity(orderRequest.quantity());

            orderRepository.save(order);


            OrderPlacedEvent orderPlacedEvent = new OrderPlacedEvent(order.getOrderNumber(),orderRequest.userDetails().email());
            log.info("Start sending orderPlacedEvent to kafkaTopic order-placed",orderPlacedEvent);
            kafkaTemplate.send("order-placed",orderPlacedEvent);
            log.info("End sending orderPlacedEvent to kafkaTopic order-placed",orderPlacedEvent);


        }
        else{
            throw new RuntimeException("Product with skucode : "+ orderRequest.skuCode()+" is not in stock");
        }
    }
}

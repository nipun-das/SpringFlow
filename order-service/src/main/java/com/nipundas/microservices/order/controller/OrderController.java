package com.nipundas.microservices.controller;


import com.nipundas.microservices.dto.OrderRequest;
import com.nipundas.microservices.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String placeOrder(@RequestBody OrderRequest orderRequest){

        orderService.placeOrder(orderRequest);
        System.out.println("order placed successfully....");
        return "Order placed successfully....";
    }
}

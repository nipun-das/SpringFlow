package com.nipundas.microservices.repository;

import com.nipundas.microservices.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Long> {
}

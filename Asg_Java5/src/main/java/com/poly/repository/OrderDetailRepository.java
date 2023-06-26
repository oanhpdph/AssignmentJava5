package com.poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.entity.OrderDetails;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetails, Integer> {

}

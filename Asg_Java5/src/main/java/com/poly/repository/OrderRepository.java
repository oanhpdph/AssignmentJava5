package com.poly.repository;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.entity.Accounts;
import com.poly.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Integer> {

	List<Orders> findByAccountId(Accounts accounts, Sort sort);

}

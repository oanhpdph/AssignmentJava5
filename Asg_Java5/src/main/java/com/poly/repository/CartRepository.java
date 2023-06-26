package com.poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.entity.Accounts;
import com.poly.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

	Cart findByAccountId(Accounts accounts);

}

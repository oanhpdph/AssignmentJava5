package com.poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.entity.Cart;
import com.poly.entity.CartDetail;
import com.poly.entity.Products;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {

	CartDetail findByIdCartAndProductId(Cart cartFind, Products products);

}

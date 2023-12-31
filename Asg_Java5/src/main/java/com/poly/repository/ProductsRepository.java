package com.poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.entity.Products;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {
}

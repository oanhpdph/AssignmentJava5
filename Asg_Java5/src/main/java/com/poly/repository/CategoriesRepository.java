package com.poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.poly.entity.Categories;

public interface CategoriesRepository extends JpaRepository<Categories, Integer> {

}

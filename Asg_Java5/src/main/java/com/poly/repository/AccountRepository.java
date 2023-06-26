package com.poly.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poly.entity.Accounts;

@Repository
public interface AccountRepository extends JpaRepository<Accounts, Integer> {

	Accounts findByEmailAndPassword(String email, String password);

	Accounts findByEmail(String email);

}

package com.jaynius.securitydemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jaynius.securitydemo.entity.Product;

@Repository
public interface ProductsRepository extends JpaRepository<Product, Integer>{

}

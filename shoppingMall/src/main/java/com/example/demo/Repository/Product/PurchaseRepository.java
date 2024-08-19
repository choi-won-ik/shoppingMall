package com.example.demo.Repository.Product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.Entity.Purchase;

public interface PurchaseRepository extends JpaRepository<Purchase, Long>{

}

package com.example.demo.Repository.Product;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

	@Query(
    		value="SELECT * FROM Category WHERE category_name = :str",
    		nativeQuery  = true
    		)
	Optional<Category> findByCategoryName(@Param("str")String categoryId);
}
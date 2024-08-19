package com.example.demo.Repository.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Member;
import com.example.demo.Entity.Product;

public interface ProductRepository extends JpaRepository<Product, Integer> {

	@Query(
    		value="SELECT * FROM Product WHERE product_no = :int",
    		nativeQuery  = true
    		)
	Optional<Product> findByProductNo(@Param("int")int productCode);

	@Query(
    		value="SELECT * FROM product WHERE product_no = (SELECT MAX(product_no) FROM product)",
    		nativeQuery  = true
    		)
	Optional<Integer> findMaxId();

	@Query(
    		value="SELECT COUNT(*) AS product_count FROM product JOIN member ON product.user = member.id WHERE member.id = :id",
    		nativeQuery  = true
    		)
	int findProductNUMByUserid(@Param("id")Long id);

	@Query(
    		value="SELECT * FROM product JOIN member ON product.user = member.id WHERE member.id = :id",
    		nativeQuery  = true
    		)
	Optional<List<Product>> findProductlistByUserid(@Param("id")Long id);

	@Query(
    		value="SELECT * FROM product WHERE product_name LIKE %:str%",
    		nativeQuery  = true
    		)
	Optional<List<Product>> findByNameContaining(@Param("str")String str);

	@Query(
    		value="SELECT user FROM product WHERE product_no =:id",
    		nativeQuery  = true
    		)
	int findUserByProductNo(@Param("id")int productCode);

}
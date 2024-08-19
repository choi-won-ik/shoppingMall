package com.example.demo.Repository.Product;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.Entity.Likey;
import com.example.demo.Entity.Member;
import com.example.demo.Entity.Product;

public interface LikeyRepository extends JpaRepository<Likey, Integer> {

	@Query(value = "SELECT COUNT(*) FROM likey WHERE likey.product = :id", nativeQuery = true)
	int findProductCount(@Param("id") int productNo);

	@Query("SELECT COUNT(l) FROM Likey l WHERE l.user = :member AND l.product = :product")
	int findUserCountByProduct(@Param("member") Member member, @Param("product") Product product);

	@Transactional
	@Modifying
	@Query("DELETE FROM Likey l WHERE l.user = :member AND l.product = :product")
	void deleteByUserAndProduct(@Param("member") Member member, @Param("product") Product product);

	@Query(value = "SELECT likey.product FROM likey WHERE likey.user = :id", nativeQuery = true)
	Optional<List<Integer>> findByUser(@Param("id") long id);

	@Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM likey WHERE user = :id", nativeQuery = true)
	long existsBylikey(@Param("id") Long id);
	
//    @Query("SELECT CASE WHEN EXISTS (SELECT 1 FROM likey WHERE user = :id) THEN TRUE ELSE FALSE END")
//    Boolean existsBylikey(@Param("id") long id);
}
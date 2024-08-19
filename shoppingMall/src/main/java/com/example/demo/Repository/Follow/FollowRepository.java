package com.example.demo.Repository.Follow;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Follow;

public interface FollowRepository extends JpaRepository<Follow, Long>{
	                
	@Query(value = "SELECT CASE WHEN COUNT(*) > 0 THEN TRUE ELSE FALSE END FROM Follow WHERE follower_id = :followerId AND following_id = :followingId" , nativeQuery = true)
	long existsByFollowerAndFollowing(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

	@Modifying
    @Query("DELETE FROM Follow f WHERE f.follower.id = :followerId AND f.following.id = :followingId")
	void deleteByFollowerAndFollowing(@Param("followerId") Long followerId, @Param("followingId") Long followingId);

    @Query(
    		value="SELECT COUNT(*) FROM Follow f WHERE f.follower_id = :id",
    		nativeQuery  = true
    		)
    Long followNUM(@Param("id")Long id);

    @Query(
    		value="SELECT COUNT(*) FROM Follow f WHERE f.following_id = :id",
    		nativeQuery  = true
    		)
    Long followingNUM(@Param("id")Long id);



}
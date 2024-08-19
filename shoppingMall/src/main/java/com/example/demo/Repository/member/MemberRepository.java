package com.example.demo.Repository.member;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.demo.Entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long>{
	@Query(
			value= "SELECT * from member WHERE userid = :overlap",
			nativeQuery  = true
			)
	Optional<Member> findByUserid(@Param("overlap") String overlap);

	@Query(
			value= "SELECT * from member WHERE userid = :str",
			nativeQuery  = true
			)
	Member UserCheck(@Param("str") String str);

	@Query(
			value= "SELECT name from member WHERE userid = :str",
			nativeQuery  = true
			)
	Optional<String> findNameByUserid(@Param("str")String str);

	@Query(
			value= "SELECT id from member WHERE userid = :userid",
			nativeQuery  = true
			)
	Long findIdByUserid(@Param("userid")String userid);

	@Query(
			value="SELECT m.* FROM member m JOIN follow f ON m.id = f.following_id WHERE f.follower_id = :id",
    		nativeQuery  = true
    		)
	Optional<List<Member>> follows(@Param("id")Long id);
	
	@Query(
			value="SELECT m.* FROM member m JOIN follow f ON m.id = f.follower_id WHERE f.following_id = :id",
			nativeQuery  = true
			)
	Optional<List<Member>> followings(@Param("id")Long id);
}
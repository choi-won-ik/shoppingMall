package com.example.demo.service.follw;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Follow;
import com.example.demo.Entity.Member;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.Follow.FollowRepository;
import com.example.demo.Repository.Product.ProductRepository;
import com.example.demo.Repository.member.MemberRepository;
import com.example.demo.dto.product.MainProduct;
import com.example.demo.dto.product.bottomMenu.FollowerList;
import com.example.demo.dto.product.bottomMenu.FollowingList;
import com.example.demo.handler.EncodeToBase64;

import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class FollowService {

	@Autowired
	private FollowRepository followRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private ProductRepository productRepository;

	public ResponseEntity<String> following(String userID, String followingID) {
		Member follower = memberRepository.findByUserid(userID).get();
		Member following = memberRepository.findByUserid(followingID).get();

		try {
			followSave(follower, following);

			return ResponseEntity.ok("join success");
		} catch (Exception e) {
			return ResponseEntity.badRequest().body(e.getMessage());
		}
	}

	@Transactional
	private void followSave(Member follower, Member following) {
		if (follower != null && following != null) {
			Follow follow = new Follow(follower, following);
			followRepository.save(follow);
		}
	}

	public boolean isFollowing(Long followerId, String meID) {
		Long followingId = memberRepository.findIdByUserid(meID);

		long num = followRepository.existsByFollowerAndFollowing(followerId, followingId);

		boolean result = (num == 1) ? true : false;
		return result;
	}

	@Transactional
	public boolean unfollow(String userID, String followingID) {
		Member follower = memberRepository.findByUserid(userID).get();
		Member following = memberRepository.findByUserid(followingID).get();

		Long followerId = follower.getId();
		Long followingId = following.getId();

		if (isFollowing(followerId, following.getUserid())) {
			followRepository.deleteByFollowerAndFollowing(followerId, followingId);
			return true;
		}
		return false;
	}

	public Long followNUM(Member member) {
		return followRepository.followNUM(member.getId());
	}

	public Long followingNUM(Member member) {
		return followRepository.followingNUM(member.getId());
	}

	public List<FollowerList> followers(Member member, Optional<String> userid) {
		List<FollowerList> li = new ArrayList<>();

		// 로그인 아이디
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<String> loginID = Optional.of(authentication.getName());
		long loginUser = 0;
		if (!loginID.equals(Optional.of("anonymousUser"))) {
			loginUser = memberRepository.findByUserid(loginID.get()).get().getId();
		}


		if (!memberRepository.follows(member.getId()).isEmpty()) {
			List<Member> list = memberRepository.follows(member.getId()).get();
			for (Member mem : list) {
				boolean result = false;
				if (loginUser != 0) {
					result = (followRepository.existsByFollowerAndFollowing(mem.getId(), loginUser) >= 1) ? true : false;
				}

				FollowerList followerList = FollowerList.builder().user(mem.getName())
						.productNUM(productRepository
						.findProductNUMByUserid(mem.getId()))
						.userid(mem.getUserid())
						.follow(result)
						.build();

				if (mem.getProfile() != null) {
					followerList.setImg("data:image/png;base64," + EncodeToBase64.encodeToBase64(mem.getProfile()));
				}
				

				li.add(followerList);
			}

		}
		return li;
	}

	public List<FollowingList> followings(Member member, Optional<String> userid) {
		List<FollowingList> li = new ArrayList<>();

		// 로그인 아이디
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<String> loginID = Optional.of(authentication.getName());
		long loginUser = 0;
		if (!loginID.equals(Optional.of("anonymousUser"))) {
			loginUser = memberRepository.findByUserid(loginID.get()).get().getId();
		}

		if (!memberRepository.follows(member.getId()).isEmpty()) {
			List<Member> list = memberRepository.followings(member.getId()).get();

			for (Member mem : list) {
				boolean result = false;
				if (loginUser != 0) {
					result = (followRepository.existsByFollowerAndFollowing(mem.getId(), loginUser) >= 1) ? true : false;
				}

				FollowingList followingList = FollowingList.builder().user(mem.getName())
						.productNUM(productRepository.findProductNUMByUserid(mem.getId()))
						.followerNUM(followRepository.followNUM(mem.getId())).userid(mem.getUserid()).follow(result)
						.build();

				// 프로필 이미지 추가
				if (mem.getProfile() != null) {
					followingList
							.setProfileImg("data:image/png;base64," + EncodeToBase64.encodeToBase64(mem.getProfile()));
				}

				// 상품 이미지 추가
				if (followingList.getProductNUM() != 0) {
					List<Product> productList = productRepository.findProductlistByUserid(mem.getId()).get();
					List<MainProduct> productImg = new ArrayList<>();
					for (Product product : productList) {
						MainProduct mainProduct = MainProduct.builder()
								.img("data:image/png;base64,"
										+ EncodeToBase64.encodeToBase64(product.getProductImage1()))
								.id(String.valueOf(product.getProductNo())).build();

						productImg.add(mainProduct);
					}
					followingList.setProductImg(productImg);
				}

				li.add(followingList);
			}
			return li;
		} else {
			return null;
		}

	}

}

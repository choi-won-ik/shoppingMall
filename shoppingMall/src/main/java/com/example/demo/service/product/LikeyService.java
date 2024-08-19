package com.example.demo.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Likey;
import com.example.demo.Entity.Member;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.Product.LikeyRepository;
import com.example.demo.Repository.Product.ProductRepository;
import com.example.demo.dto.product.ProductList;
import com.example.demo.handler.EncodeToBase64;

import lombok.extern.log4j.Log4j2;



@Service
@Log4j2
public class LikeyService {
	@Autowired
	private LikeyRepository likeyRepository;
	@Autowired
	private ProductRepository productRepository;
	
	public boolean add(Optional<Product> product,Optional<Member> member) {
		boolean bool = (likeyRepository.findUserCountByProduct(member.get(), product.get()) == 1) ? true : false;
		
		Likey likey = Likey.builder()
				.user(member.get())
				.product(product.get())
				.build();
		if(bool==false) {
			likeyRepository.save(likey);
			
			return true;
		}else {
			likeyRepository.deleteByUserAndProduct(likey.getUser(),likey.getProduct());
			
			return false;
		}
	}

	public int likeyNUM(int productNo) {
		return likeyRepository.findProductCount(productNo);
	}

	public List<ProductList> userLikeyList(Member member) {
		log.info(member.getId());
		Optional<List<Integer>> op= likeyRepository.findByUser(member.getId());
		
		List<ProductList> list = new ArrayList<>();
		if(op.isEmpty()){
			return null;
		}else {
			for (int num : op.get()) {
				Product product = productRepository.findByProductNo(num).get();
				
				ProductList productList =ProductList.builder()
						.id(product.getUser().getUserid())
						.productName(product.getProductName())
						.price(0)
						.img("data:image/png;base64," + EncodeToBase64.encodeToBase64(product.getProductImage1()))
						.productNo(num)
						.build();
				
				list.add(productList);
			}
			
			return list;
		}
	}

	public boolean meLikey(Long id) {
		boolean result = (likeyRepository.existsBylikey(id) == 1) ? true : false;
		
		return result;
	}
}

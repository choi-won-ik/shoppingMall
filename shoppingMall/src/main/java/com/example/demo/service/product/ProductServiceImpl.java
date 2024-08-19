package com.example.demo.service.product;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Member;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.Follow.FollowRepository;
import com.example.demo.Repository.Product.ProductRepository;
import com.example.demo.Repository.member.MemberRepository;
import com.example.demo.dto.ProductSearch;
import com.example.demo.dto.product.MainProduct;
import com.example.demo.dto.product.ProductInformation;
import com.example.demo.dto.product.ProductList;
import com.example.demo.dto.product.Seller;
import com.example.demo.handler.EncodeToBase64;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private MemberRepository memberRepository;
	@Autowired
	private FollowRepository followRepository;
	

	public ProductServiceImpl(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	// 상품저장
	public Product saveProduct(Product product) throws Exception {

		return productRepository.save(product);
	}

	@Override
	public Optional<Product> findByProductNo(int productCode) {
		productRepository.findByProductNo(productCode);
		return productRepository.findByProductNo(productCode);
	}

	@Override
	public StringBuilder image(Optional<byte[]> op) {
		StringBuilder imageHtml = new StringBuilder();

		if (op.get() != null) {
			String base64Image = "data:image/png;base64," + EncodeToBase64.encodeToBase64(op.get());
			return imageHtml.append("<img src=\"").append(base64Image).append("\" alt=\"\" />");
		} else {
			return imageHtml.append("<p>Image is not available.</p>");
		}
	}



	@Override
	public ProductInformation productInformation(Product product) {
		ProductInformation productInformation = ProductInformation.builder()
				.productNo(product.getProductNo())
				.productName(product.getProductName())
				.user(product.getUser())
				.productDescription(product.getProductDescription())
				.productPrice(product.getProductPrice())
				.quantity(product.getQuantity()).build();

		return productInformation;
	}

	@Override
	public List<MainProduct> allProduct() {
		List<Product> list = productRepository.findAll();
		List<MainProduct> productList = new ArrayList<>();
		for (Product product : list) {
			
			MainProduct add = MainProduct.builder()
					.id(String.valueOf(product.getProductNo()))
					.img("data:image/png;base64," + EncodeToBase64.encodeToBase64(product.getProductImage1()))
					.build();

			productList.add(add);
		}
		
		return productList;
	}

	@Override
	public int productNUM(Long id) {
		return productRepository.findProductNUMByUserid(id);
	}

	@Override
	public List<ProductList> productList(Long id) {
		List<ProductList> list = new ArrayList<>();
		if(!productRepository.findProductlistByUserid(id).isEmpty()) {
			List<Product> products = productRepository.findProductlistByUserid(id).get();
			
			
			for (Product product : products) {
				ProductList productList = ProductList.builder()
						.id(String.valueOf(product.getProductNo()))
						.img("data:image/png;base64," + EncodeToBase64.encodeToBase64(product.getProductImage1()))
						.productName(product.getProductName())
						.price(product.getProductPrice())
						.productStreetaddress(product.getProductStreetaddress())
						.build();
				
				list.add(productList);
			}
			return list;
		}else {
			return null;
		}
	}

	@Override
	public List<ProductSearch> productSearch(String str) {
		Optional<List<Product>> op = productRepository.findByNameContaining(str);
		List<ProductSearch> result = new ArrayList<>();
		if(op.isEmpty()) {
			return null;
		}else {
			List<Product> list = op.get();
			
			for (Product product : list) {
				ProductSearch productSearch = ProductSearch.builder()
						.productName(product.getProductName())
						.productNum(product.getProductNo())
						.build();
				
				result.add(productSearch);
			}
			
			return result;
		}
	}

	@Override
	public Seller seller(int productCode) {
		long id = productRepository.findUserByProductNo(productCode);
		Member member = memberRepository.findById(id).get();
		
		
		
		Optional<List<Product>> op= productRepository.findProductlistByUserid(id);
		List<ProductList> list = new ArrayList<>();
		
		if(!op.isEmpty()) {
			for (Product product : op.get()) {
				ProductList productList = ProductList.builder()
						.price(product.getProductPrice())
						.img("data:image/png;base64," + EncodeToBase64.encodeToBase64(product.getProductImage1()))
						.build();
				
				list.add(productList);
			}

			Seller seller = Seller.builder()
					.userid(member.getUserid())
					.name(member.getName())
					.product(list)
					.productNUM(op.get().size())
					.followNUM(followRepository.followNUM(id))
					.build();
			
			if(member.getProfile()!=null) {
				StringBuilder imageHtml = new StringBuilder();
				String base64Image = "data:image/png;base64," + EncodeToBase64.encodeToBase64(member.getProfile());
				imageHtml.append("<img src=\"").append(base64Image).append("\" class='seller-img' alt=\"\" />");
				seller.setProfile(imageHtml);
			}
			return seller;
		}
		else {
			return null;
		}
		
	}
}

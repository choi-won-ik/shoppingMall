package com.example.demo.controller.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.example.demo.Entity.Category;
import com.example.demo.Entity.Member;
import com.example.demo.Entity.Product;
import com.example.demo.dto.product.ProductInformation;
import com.example.demo.dto.product.Seller;
import com.example.demo.handler.UserHandler;
import com.example.demo.service.category.CategoryService;
import com.example.demo.service.follw.FollowService;
import com.example.demo.service.member.MemberService;
import com.example.demo.service.product.LikeyService;
import com.example.demo.service.product.PayService;
import com.example.demo.service.product.ProductService;

import jakarta.servlet.http.HttpServletRequest;

@ControllerAdvice
@Controller
@RequestMapping("/product")
public class ProductCotroller {

	@Autowired
	private ProductService productService;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private PayService payService;
	@Autowired
	private LikeyService likeyService;
	@Autowired
	private FollowService followService;

	// 상품 추가 페이지
	@GetMapping("/productADD")
	public String showAddProductForm(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("categories", categoryService.getAllCategories());
		return "product/productADD";
	}

	// 상품 추가
	@PostMapping("/add")
	public String addProduct(@RequestParam("image1") MultipartFile image1, @RequestParam("image2") MultipartFile image2,
			@RequestParam("image3") MultipartFile image3, @RequestParam("productName") String productName,
			@RequestParam("categoryId") String categoryId,
			@RequestParam("productDescription") String productDescription, @RequestParam("price") String priceStr,
			@RequestParam("productStreetaddress") String productStreetaddress,
			@RequestParam("productDetailaddress") String productDetailaddress, @RequestParam("quantity") int quantity,
			HttpServletRequest request) throws Exception {
		// 로그인 아이디
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String loginID = authentication.getName();

		// 이미지 데이터 처리 및 저장 로직
		byte[] image1Data = image1.getBytes();
		byte[] image2Data = image2.getBytes();
		byte[] image3Data = image3.getBytes();

		// 로그인 되어있는 유저 정보
		Member member = memberService.introductionFind(loginID);
		// 카테고리 정보
		Category category = categoryService.getCategoryById(categoryId);
		// 가격 정수화
		int price = Integer.parseInt(priceStr);

		Product product = Product.builder().productDescription(productDescription)
				.productDetailaddress(productDetailaddress).productImage1(image1Data).productImage2(image2Data)
				.productImage3(image3Data).productName(productName).productPrice(price)
				.productStreetaddress(productStreetaddress).user(member).quantity(quantity).category(category).build();

		Product add = productService.saveProduct(product);

		return "redirect:product/" + add.getProductNo();
	}

	// 상품 페이지
	@GetMapping("product/{productCode}")
	public String product(@PathVariable("productCode") int productCode, Model model, HttpServletRequest request) {
		// 로그인 아이디
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		Optional<String> loginID = Optional.of(authentication.getName());
		
		Optional<Product> product = productService.findByProductNo(productCode);

		ProductInformation item = productService.productInformation(product.get());

		// 필요 상품 정보
		model.addAttribute("Description", item.getProductDescription());
		model.addAttribute("productName", item.getProductName());
		model.addAttribute("price", item.getProductPrice());
		model.addAttribute("quantity", item.getQuantity());
		model.addAttribute("productCode", productCode);

		// 상품 좋아요 수
		model.addAttribute("likeyNUM", likeyService.likeyNUM(item.getProductNo()));

		// 상품 판매자
		Seller seller = productService.seller(productCode);
		model.addAttribute("seller", seller);

		model.addAttribute("kakaoPay", payService.kakaoDto(product.get()));

		// img 정의
		model.addAttribute("img1", productService.image(Optional.of(product.get().getProductImage1())));
		model.addAttribute("img2", productService.image(Optional.of(product.get().getProductImage2())));
		model.addAttribute("img3", productService.image(Optional.of(product.get().getProductImage3())));
//		//상세정보
//		model.addAttribute("description",product.get().getProductDescription());
//		// 수량
		model.addAttribute("totalQuantity", product.get().getQuantity());

		boolean follow = false;
		boolean myPage = false;
		
		// 사용자 로그인이 되어 있을 경우
		if (!loginID.equals(Optional.of("anonymousUser"))) {
			Member member = memberService.overlap(loginID.get()).get();
			
			// 로그인 되어있는 유저의 상품일 경우
			if(seller.getUserid()==loginID.get()) {
				myPage = true;
			}
			// 로그인 되어있는 유저의 상품이 아닐경우
			else {
				model.addAttribute("meLikey", likeyService.meLikey(member.getId()));
				follow = followService.isFollowing(memberService.overlap(seller.getUserid()).get().getId(), loginID.get());
			}
		}
		
		model.addAttribute("follow", follow ? "true" : "false");
		model.addAttribute("myPage", myPage ? "true" : "false");
		return "product/product";
	}
}

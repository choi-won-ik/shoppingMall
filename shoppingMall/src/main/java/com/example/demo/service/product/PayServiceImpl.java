package com.example.demo.service.product;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.example.demo.Entity.Product;
import com.example.demo.Entity.Purchase;
import com.example.demo.Repository.Product.ProductRepository;
import com.example.demo.Repository.Product.PurchaseRepository;
import com.example.demo.dto.kakaoPay.ApproveRequest;
import com.example.demo.dto.kakaoPay.KakaoPayDTO;
import com.example.demo.dto.kakaoPay.ReadyRequest;
import com.example.demo.dto.kakaoPay.ReadyResponse;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class PayServiceImpl implements PayService {
	@Value("DEVADB7E89440DE66AE4168B9AD87D7199BE4E74")
	private String kakaopaySecretKey;

	@Value("TC0ONETIME")
	private String cid;

	@Value("http://localhost:8080")
	private String sampleHost;

	private String tid;
	private static KakaoPayDTO product;
	@Autowired
	private ProductRepository productRepository;
	@Autowired
	private PurchaseRepository purchaseRepository;

	
	@Override
	public ReadyResponse ready(KakaoPayDTO kakaoPay) {
		// Request header
		HttpHeaders headers = new HttpHeaders();
		// 여기까진 ok
		headers.add("Authorization", "DEV_SECRET_KEY " + kakaopaySecretKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		product = kakaoPay;

		// Request param
		ReadyRequest readyRequest = ReadyRequest.builder().cid(cid).partnerOrderId(kakaoPay.getPartnerOrderId())
				.partnerUserId(kakaoPay.getPartnerUserId()).itemName(kakaoPay.getItemName())
				.quantity(kakaoPay.getQuantity()).totalAmount(kakaoPay.getTotalAmount())
				.taxFreeAmount(kakaoPay.getTaxFreeAmount()).approvalUrl(sampleHost + "/approve")
				.cancelUrl(sampleHost + "/cancel").failUrl(sampleHost + "/fail").build();

		// Send reqeust
		HttpEntity<ReadyRequest> entityMap = new HttpEntity<>(readyRequest, headers);

		ResponseEntity<ReadyResponse> response = new RestTemplate()
				.postForEntity("https://open-api.kakaopay.com/online/v1/payment/ready", entityMap, ReadyResponse.class);

		ReadyResponse readyResponse = response.getBody();

		// 주문번호와 TID를 매핑해서 저장해놓는다.
		// Mapping TID with partner_order_id then save it to use for approval request.
		this.tid = readyResponse.getTid();
		return readyResponse;
	}

	@Override
	public String approve(String pgToken) {
		// ready할 때 저장해놓은 TID로 승인 요청
		// Call “Execute approved payment” API by pg_token, TID mapping to the current
		// payment transaction and other parameters.
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "SECRET_KEY " + kakaopaySecretKey);
		headers.setContentType(MediaType.APPLICATION_JSON);

		// Request param
		ApproveRequest approveRequest = ApproveRequest.builder().cid(cid).tid(tid)
				.partnerOrderId(product.getPartnerOrderId()).partnerUserId(product.getPartnerUserId()).pgToken(pgToken)
				.build();

		// Send Request
		HttpEntity<ApproveRequest> entityMap = new HttpEntity<>(approveRequest, headers);
		try {
			ResponseEntity<String> response = new RestTemplate()
					.postForEntity("https://open-api.kakaopay.com/online/v1/payment/approve", entityMap, String.class);

			// 승인 결과를 저장한다.
			// save the result of approval
			String approveResponse = response.getBody();

			String productCODE = product.getPartnerOrderId();
			String result = productCODE.replace("product", "");
			int productID = Integer.parseInt(result);

			Optional<Product> Op = productRepository.findByProductNo(productID);
			// 수량 채크
			productUpdate(Op.get(), product.getQuantity());

			// 구매내역 저장
			Purchase purchase = Purchase.builder().product(Op.get()).quantity(product.getQuantity())
					.totalAmount(product.getTotalAmount()).productStreetaddress(product.getProductStreetaddress())
					.productDetailaddress(product.getProductDetailaddress()).build();
			purchaseRepository.save(purchase);

			return approveResponse;
		} catch (HttpStatusCodeException ex) {
			return ex.getResponseBodyAsString();
		}
	}

	// 제품구매 시 제품 총량 수정
	@Transactional
	public void productUpdate(Product p, int quantity) {
		p.setQuantity(p.getQuantity() - quantity);

		productRepository.save(p);
	}


	@Override
	public KakaoPayDTO kakaoDto(Product product) {
		// 상품코드
		String productCODE = "product"+String.valueOf(product.getProductNo());
		
		KakaoPayDTO kakaoPayDTO = KakaoPayDTO.builder()
				.partnerOrderId(productCODE)
				.partnerUserId(product.getUser().getUserid())
				.itemName(product.getProductName())
				.totalAmount(product.getProductPrice())
				.build();
		
		return kakaoPayDTO;
	}

}

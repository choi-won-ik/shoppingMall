package com.example.demo.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Purchase {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(unique = true, nullable = false)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "user")
	private Member user;

	@ManyToOne
	@JoinColumn(name = "product")
	private Product product;

	// 상품 갯수
	@Column(nullable=false)
	private int quantity;

	// 총액
	@Column(nullable=false)
	private int totalAmount;
	// 주소
	@Column(name = "product_StreetAddress" , nullable= false)
	private String productStreetaddress;
	// 상세주소
	@Column(name = "product_DetailAddress",nullable= false)
	private String productDetailaddress;
}

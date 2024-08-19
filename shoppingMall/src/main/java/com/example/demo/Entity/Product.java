package com.example.demo.Entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "product")
@ToString(exclude = "category")
public class Product {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "product_no")
	private int productNo;

	@ManyToOne
	@JoinColumn(name = "user")
	private Member user;

	@Column(name = "product_name", nullable = false)
	private String productName;

	@Column(name = "product_description")
	private String productDescription;

	@Column(name = "product_price")
	private int productPrice;

//	@Column(name = "product_Address")
//	private String productAddress;

	@Column(name = "product_StreetAddress")
	private String productStreetaddress;

	@Column(name = "product_DetailAddress")
	private String productDetailaddress;

	@Lob
	@Column(name = "product_image1", columnDefinition = "LONGBLOB")
	private byte[] productImage1;

	@Lob
	@Column(name = "product_image2", columnDefinition = "LONGBLOB")
	private byte[] productImage2;

	@Lob
	@Column(name = "product_image3", columnDefinition = "LONGBLOB")
	private byte[] productImage3;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_no")
	private Category category;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Purchase> purchase;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Likey> likey;

	@Column(nullable = false)
	private int quantity;
}
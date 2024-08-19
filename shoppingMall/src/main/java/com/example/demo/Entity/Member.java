package com.example.demo.Entity;

import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Member {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(unique = true, nullable = false)
	private Long id;

	@Column(unique = true, nullable = false, length = 11)
	private String userid;

	@Column(nullable = false)
	private String pw;

	@Column(nullable = false, length = 10)
	private String name;

	@Column(nullable = false, length = 11)
	private long phone;

	@Column(nullable = false, length = 6)
	private long birthday;

	private String introduction;

	@Lob
	@Column(columnDefinition = "LONGBLOB")
	private byte[] profile;

	@Column(nullable = false)
	private String time;

	@OneToMany(mappedBy = "follower", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Follow> followers;

	@OneToMany(mappedBy = "following", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Follow> followings;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Product> product;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Purchase> purchase;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Likey> likey;
}
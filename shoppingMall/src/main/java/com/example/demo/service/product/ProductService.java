package com.example.demo.service.product;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Member;
import com.example.demo.Entity.Product;
import com.example.demo.dto.ProductSearch;
import com.example.demo.dto.product.MainProduct;
import com.example.demo.dto.product.ProductInformation;
import com.example.demo.dto.product.ProductList;
import com.example.demo.dto.product.Seller;

@Service
public interface ProductService {
	
	Product saveProduct(Product product) throws Exception;

	Optional<Product> findByProductNo(int productCode);

	StringBuilder image(Optional<byte[]> optional);

	ProductInformation productInformation(Product product);

	List<MainProduct> allProduct();

	int productNUM(Long id) ;

	List<ProductList> productList(Long id);

	List<ProductSearch> productSearch(String str);

	Seller seller(int productCode);
	
}

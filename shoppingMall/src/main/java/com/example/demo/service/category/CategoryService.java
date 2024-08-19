package com.example.demo.service.category;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.Entity.Category;

@Service
public interface CategoryService {
	
	Category saveCategory(Category category);

	List<Category> getAllCategories();
	
	Category getCategoryById(String categoryId);

}

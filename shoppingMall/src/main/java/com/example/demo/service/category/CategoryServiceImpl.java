package com.example.demo.service.category;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Entity.Category;
import com.example.demo.Repository.Product.CategoryRepository;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CategoryServiceImpl implements CategoryService {

	 @Autowired
	    private CategoryRepository categoryRepository;
	
	@Override
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}

	@Override
	public List<Category> getAllCategories() {

		 return categoryRepository.findAll();
	}

	@Override
	public Category getCategoryById(String categoryId) {
		Optional<Category> category = categoryRepository.findByCategoryName(categoryId);
		
		if(category.isEmpty()) {
			Category add = Category.builder()
					.categoryName(categoryId)
					.build();
					
			categoryRepository.save(add);
			
			return add;
		}else {
			return category.get();
		}
	}

}

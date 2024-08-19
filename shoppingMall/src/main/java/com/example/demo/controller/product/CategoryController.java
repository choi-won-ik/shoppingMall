package com.example.demo.controller.product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.demo.Entity.Category;
import com.example.demo.service.category.CategoryService;
import com.example.demo.service.product.ProductService;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;
    
    @GetMapping
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }
    
    // 추가창 이동
    @GetMapping("/add")
    public String showCategoryForm(Model model) {
        model.addAttribute("category", new Category());
        return "category/addCategory";
    }

    @PostMapping("/add")
    public String createCategory(@ModelAttribute("category") Category category) {
        Category savedCategory = categoryService.saveCategory(category);
        return "redirect:/categories";
    }

//    @PostMapping
//    public Category createCategory(@RequestParam String name, @RequestParam(required = false) Long parentId) {
//        return categoryService.createCategory(name, parentId);
//    }

//    @GetMapping("/{parentId}/subcategories")
//    public List<Category> getSubCategories(@PathVariable Long parentId) {
//        return categoryService.getSubCategories(parentId);
//    }
}
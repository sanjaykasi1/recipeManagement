package com.example.recipe_rest_api.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.recipe_rest_api.model.Category;
import com.example.recipe_rest_api.repository.CategoryRepository;

import jakarta.transaction.Transactional;

import java.util.List;


@Service
@Transactional
public class CategoryService{
	@Autowired
	private CategoryRepository cr;
	
	public Category saveCategory(Category category) {
		return cr.save(category);
	}
	
	public Category getCategory(Long id) {
		Category category = cr.findById(id).orElse(null);
        System.out.println("Category: " + category);
        return category;
    }
	
	public List<Category> getAllCategories(){
		return cr.findAll();
	}
	
	public void removeCategory(Long id) {
		cr.deleteById(id);
	}
}

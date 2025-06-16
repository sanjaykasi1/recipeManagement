package com.example.recipe_rest_api.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.recipe_rest_api.model.Category;
import com.example.recipe_rest_api.model.Recipe;
import com.example.recipe_rest_api.repository.CategoryRepository;
import com.example.recipe_rest_api.repository.RecipeRepository;

import jakarta.transaction.Transactional;


@SuppressWarnings("unused")
@Service
@Transactional
public class RecipeService {
	@Autowired
	private RecipeRepository rr;
	
	public Recipe saveRecipe( Recipe recipe) {
		//Category category = cr.findById(c1.getId()).orElseThrow();
     //   recipe.setCategory(c1);
        return rr.save(recipe);
		//rr.saveAndFlush(recipe);
	}
	
	public Recipe getRecipe(Long id) {
		Recipe recipe = rr.findById(id).orElse(null);
        return recipe;
    }
	
	public List<Recipe> getAllRecipes(){
		return rr.findAll();
	}
	
	public void removeRecipe(Long id) {
		rr.deleteById(id);
	}
}

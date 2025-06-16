package com.example.recipe_rest_api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.recipe_rest_api.model.Category;
import com.example.recipe_rest_api.model.Ingredient;
import com.example.recipe_rest_api.model.Recipe;
import com.example.recipe_rest_api.service.CategoryService;
import com.example.recipe_rest_api.service.RecipeService;

@RestController
public class AppController {
	@Autowired
	private CategoryService cs;
	
	@Autowired
	private RecipeService rs;
	
	@GetMapping("/test")
	public ResponseEntity<String> test(){
		return new ResponseEntity<String>("Welcome to Recipes App, now with REST API", HttpStatus.OK);
	}
	
	@PostMapping("/category/save")
	public ResponseEntity<Category> saveCategory(@RequestBody Category category){
		try{Category c = cs.saveCategory(category);
		return new ResponseEntity<Category>(c, HttpStatus.OK);}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	
	@GetMapping("/category/all")
	public  ResponseEntity<List<Category>> getAllCategories(){
		List<Category> c = cs.getAllCategories();
		return new ResponseEntity<List<Category>>(c, HttpStatus.OK);
	}
	
	
	
	@PostMapping("/recipe/save")
	public ResponseEntity<Recipe> saveRecipe(@RequestBody Recipe recipe){
		try{
		recipe.getCategory().getRecipes().add(recipe);
		List<Ingredient> ingredients = recipe.getIngredients();
		for(Ingredient i : ingredients) {
			i.setRecipe(recipe);
		}
		Recipe r = rs.saveRecipe(recipe);
		
		return new ResponseEntity<Recipe>(r, HttpStatus.OK);}
		catch(Exception e) {
			e.printStackTrace();
		}
		return null;
		
	}
	
	@GetMapping("/recipe/all")
	public  ResponseEntity<List<Recipe>> getAllRecipes(){
		List<Recipe> r = rs.getAllRecipes();
		return new ResponseEntity<List<Recipe>>(r, HttpStatus.OK);
	}
	
	@PostMapping("/recipe/delete/{id}")
		public ResponseEntity<String> deleteRecipe(@PathVariable("id")Long id){
			rs.removeRecipe(id);
			return new ResponseEntity<String>("Deleted Recipe with id :"+String.valueOf(id), HttpStatus.OK);
		}
	
	@PostMapping("/category/delete/{id}")
	public ResponseEntity<String> deleteCategory(@PathVariable("id")Long id){
		cs.removeCategory(id);
		return new ResponseEntity<String>("Deleted Category with id :"+String.valueOf(id), HttpStatus.OK);
	}
	
	@PostMapping("/recipe/update/{id}")
	public ResponseEntity<Recipe> updateRecipe(@PathVariable("id")Long id,@RequestBody Recipe recipe){
		Recipe r = rs.getRecipe(id);
		r.setName(recipe.getName());
		r.setDescription(recipe.getDescription());
		r.setTime(recipe.getTime());
		r.setCategory(recipe.getCategory());
		r.setIngredients(recipe.getIngredients());
		
		recipe.getCategory().getRecipes().add(r);
		List<Ingredient> ingredients = recipe.getIngredients();
		for(Ingredient i : ingredients) {
			i.setRecipe(r);
		}
		rs.saveRecipe(r);
		return new ResponseEntity<Recipe>(r, HttpStatus.OK);
	}
	
	}

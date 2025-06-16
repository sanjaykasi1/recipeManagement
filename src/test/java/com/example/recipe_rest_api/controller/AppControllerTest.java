package com.example.recipe_rest_api.controller;

import com.example.recipe_rest_api.model.Category;
import com.example.recipe_rest_api.model.Ingredient;
import com.example.recipe_rest_api.model.Recipe;
import com.example.recipe_rest_api.service.CategoryService;
import com.example.recipe_rest_api.service.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AppController.class)
public class AppControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CategoryService categoryService;

    @MockBean
    private RecipeService recipeService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testWelcomeMessage() throws Exception {
        mockMvc.perform(get("/test"))
                .andExpect(status().isOk())
                .andExpect(content().string("Welcome to Recipes App, now with REST API"));
    }

    @Test
    public void testSaveCategory() throws Exception {
        //A
    	Category category = new Category();
        category.setId(1L);
        category.setName("Dessert");

        //Make fake call - part of A 
        Mockito.when(categoryService.saveCategory(any(Category.class))).thenReturn(category);

        //Action & Assertion
        mockMvc.perform(post("/category/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(category)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(category.getId()))
                .andExpect(jsonPath("$.name").value(category.getName()));
    }

    @Test
    public void testGetAllCategories() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("Dessert");

        Mockito.when(categoryService.getAllCategories()).thenReturn(Collections.singletonList(category));

        mockMvc.perform(get("/category/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(category.getId()))
                .andExpect(jsonPath("$[0].name").value(category.getName()));
    }

    @Test
    public void testSaveRecipe() throws Exception {
        Category category = new Category();
        category.setId(1L);
        category.setName("Dessert");

        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Sugar");
        ingredient.setQuantity("2 cups");

        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Cake");
        recipe.setDescription("Delicious cake");
        recipe.setTime(60);
        recipe.setCategory(category);
        recipe.setIngredients(Collections.singletonList(ingredient));

        Mockito.when(recipeService.saveRecipe(any(Recipe.class))).thenReturn(recipe);

        mockMvc.perform(post("/recipe/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(recipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(recipe.getId()))
                .andExpect(jsonPath("$.name").value(recipe.getName()));
    }

    @Test
    public void testGetAllRecipes() throws Exception {
        Recipe recipe = new Recipe();
        recipe.setId(1L);
        recipe.setName("Cake");

        Mockito.when(recipeService.getAllRecipes()).thenReturn(Collections.singletonList(recipe));

        mockMvc.perform(get("/recipe/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(recipe.getId()))
                .andExpect(jsonPath("$[0].name").value(recipe.getName()));
    }

    @Test
    public void testDeleteRecipe() throws Exception {
        Long recipeId = 1L;
        doNothing().when(recipeService).removeRecipe(recipeId);

        mockMvc.perform(post("/recipe/delete/{id}", recipeId))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Recipe with id :" + recipeId));
    }

    @Test
    public void testDeleteCategory() throws Exception {
        Long categoryId = 1L;
        doNothing().when(categoryService).removeCategory(categoryId);

        mockMvc.perform(post("/category/delete/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(content().string("Deleted Category with id :" + categoryId));
    }

    @Test
    public void testUpdateRecipe() throws Exception {
        Long recipeId = 1L;

        Category category = new Category();
        category.setId(1L);
        category.setName("Dessert");

        Ingredient ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Sugar");
        ingredient.setQuantity("2 cups");

        Recipe existingRecipe = new Recipe();
        existingRecipe.setId(recipeId);
        existingRecipe.setName("Old Cake");
        existingRecipe.setDescription("Old description");
        existingRecipe.setTime(50);
        existingRecipe.setCategory(category);
        existingRecipe.setIngredients(Collections.singletonList(ingredient));

        Recipe updatedRecipe = new Recipe();
        updatedRecipe.setId(recipeId);
        updatedRecipe.setName("New Cake");
        updatedRecipe.setDescription("New description");
        updatedRecipe.setTime(60);
        updatedRecipe.setCategory(category);
        updatedRecipe.setIngredients(Collections.singletonList(ingredient));

        Mockito.when(recipeService.getRecipe(recipeId)).thenReturn(existingRecipe);
        Mockito.when(recipeService.saveRecipe(any(Recipe.class))).thenReturn(updatedRecipe);

        mockMvc.perform(post("/recipe/update/{id}", recipeId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedRecipe)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedRecipe.getId()))
                .andExpect(jsonPath("$.name").value(updatedRecipe.getName()))
                .andExpect(jsonPath("$.description").value(updatedRecipe.getDescription()))
                .andExpect(jsonPath("$.time").value(updatedRecipe.getTime()));
    }
}

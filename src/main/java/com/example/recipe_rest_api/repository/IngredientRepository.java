package com.example.recipe_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.recipe_rest_api.model.Ingredient;



@Repository
public interface IngredientRepository extends JpaRepository<Ingredient, Long>{

}

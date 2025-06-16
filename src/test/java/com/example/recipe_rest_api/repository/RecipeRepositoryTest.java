package com.example.recipe_rest_api.repository;

import com.example.recipe_rest_api.model.Ingredient;
import com.example.recipe_rest_api.model.Recipe;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class RecipeRepositoryTest {

	@Autowired
	private RecipeRepository recipeRepository;

	@Test
	@DisplayName("Test saving and retrieving a Recipe")
	public void testSaveAndFindById() {
		// Arrange
		Recipe recipe = new Recipe("Pasta", "Delicious Italian pasta");
		recipe = recipeRepository.save(recipe);

		// Act
		Optional<Recipe> retrievedRecipe = recipeRepository.findById(recipe.getId());

		System.out.println(recipe);

		// Assert
		assertThat(retrievedRecipe).isPresent();
		assertThat(retrievedRecipe.get().getName()).isEqualTo("Pasta");
		assertThat(retrievedRecipe.get().getDescription()).isEqualTo("Delicious Italian pasta");
	}

	@Test
	@DisplayName("Test deleting a Recipe")
	public void testDeleteRecipe() {
		// Arrange
		Recipe recipe = new Recipe("Salad", "Fresh vegetable salad");
		recipe = recipeRepository.save(recipe);
		Long recipeId = recipe.getId();

		// Act
		recipeRepository.deleteById(recipeId);
		Optional<Recipe> deletedRecipe = recipeRepository.findById(recipeId);

		// Assert
		assertThat(deletedRecipe).isNotPresent();
	}

	@Test
	@DisplayName("Test saving and retrieving a Recipe with Ingredients")
	public void testSaveAndFindByIdWithIngredients() {
		// Arrange
		Recipe recipe = new Recipe("Pasta", "Delicious Italian pasta");

		Ingredient ingredient1 = new Ingredient("Tomato", "2 cups", recipe);
		Ingredient ingredient2 = new Ingredient("Basil", "5 leaves", recipe);

		recipe.getIngredients().add(ingredient1);
		recipe.getIngredients().add(ingredient2);

		// Act
		Recipe savedRecipe = recipeRepository.save(recipe);
		Optional<Recipe> retrievedRecipeOptional = recipeRepository.findById(savedRecipe.getId());

		// Assert
		assertThat(retrievedRecipeOptional).isPresent();
		Recipe retrievedRecipe = retrievedRecipeOptional.get();
		assertThat(retrievedRecipe.getName()).isEqualTo("Pasta");
		assertThat(retrievedRecipe.getIngredients()).hasSize(2);
		assertThat(retrievedRecipe.getIngredients()).extracting(Ingredient::getName).containsExactlyInAnyOrder("Tomato",
				"Basil");
	}
}

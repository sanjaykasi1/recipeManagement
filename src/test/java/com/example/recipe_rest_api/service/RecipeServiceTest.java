package com.example.recipe_rest_api.service;

import com.example.recipe_rest_api.model.Recipe;
import com.example.recipe_rest_api.repository.RecipeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class RecipeServiceTest {

    @Mock
    private RecipeRepository recipeRepository;

    @InjectMocks
    private RecipeService recipeService;

    private Recipe recipe1;
    private Recipe recipe2;

    @BeforeEach
    public void setUp() {
        recipe1 = new Recipe();
        //recipe1.setId(1L);
        recipe1.setName("Spaghetti Bolognese");

        recipe2 = new Recipe();
        recipe2.setId(2L);
        recipe2.setName("Chicken Curry");
    }

    @Test
    public void testSaveRecipe() {
    	
    	//Assign - Given
        when(recipeRepository.save(recipe2)).thenReturn(recipe2);

        //Action -
        Recipe savedRecipe = recipeService.saveRecipe(recipe1);
        
        System.out.println(" ############################# id :"+savedRecipe.getName());

        assertThat(savedRecipe).isNotNull();
        assertThat(savedRecipe.getName()).isEqualTo("Spaghetti Bolognese");
        verify(recipeRepository, times(1)).save(recipe1);
    }

    @Test
    public void testGetRecipe() {
        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe1));

        Recipe foundRecipe = recipeService.getRecipe(1L);

        assertThat(foundRecipe).isNotNull();
        assertThat(foundRecipe.getName()).isEqualTo("Spaghetti Bolognese");
        verify(recipeRepository, times(1)).findById(1L);
    }

    @Test
    public void testGetAllRecipes() {
        when(recipeRepository.findAll()).thenReturn(Arrays.asList(recipe1, recipe2));

        List<Recipe> recipes = recipeService.getAllRecipes();

        assertThat(recipes).hasSize(2);
        verify(recipeRepository, times(1)).findAll();
    }

    @Test
    public void testRemoveRecipe() {
        doNothing().when(recipeRepository).deleteById(1L);

        recipeService.removeRecipe(1L);

        verify(recipeRepository, times(1)).deleteById(1L);
    }
}

package com.example.recipe_rest_api.service;

import com.example.recipe_rest_api.model.Ingredient;
import com.example.recipe_rest_api.repository.IngredientRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestPropertySource(locations = "classpath:application-test.properties")
public class IngredientServiceTest {

    @Mock
    private IngredientRepository ingredientRepository;

    @InjectMocks
    private IngredientService ingredientService;

    private Ingredient ingredient;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ingredient = new Ingredient();
        ingredient.setId(1L);
        ingredient.setName("Tomato");
        ingredient.setQuantity("2 cups");
    }

    @Test
    public void testSaveIngredient() {
        ingredientService.saveIngredient(ingredient);
        verify(ingredientRepository, times(1)).save(ingredient);
    }

    @Test
    public void testGetIngredient() {
        when(ingredientRepository.findById(1L)).thenReturn(java.util.Optional.of(ingredient));
        ingredientService.getIngredient(1L);
        verify(ingredientRepository, times(1)).findById(1L);
    }
}

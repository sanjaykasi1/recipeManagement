package com.example.recipe_rest_api.service;

import com.example.recipe_rest_api.model.Category;
import com.example.recipe_rest_api.repository.CategoryRepository;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.data.mapping.AccessOptions.SetOptions.Propagation;
import org.springframework.transaction.annotation.Propagation;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@Transactional
public class CategoryServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    private Category sampleCategory;

    @BeforeEach
    void setUp() {
        sampleCategory = new Category();
        sampleCategory.setId(1L);
        sampleCategory.setName("Dessert");
    }

    @Test
    @DisplayName("Should save a category successfully")
    void testSaveCategory() {
        when(categoryRepository.save(sampleCategory)).thenReturn(sampleCategory);

        Category savedCategory = categoryService.saveCategory(sampleCategory);

        assertThat(savedCategory).isNotNull();
        assertThat(savedCategory.getName()).isEqualTo("Dessert");
        verify(categoryRepository, times(1)).save(sampleCategory);
    }

    @Test
    @DisplayName("Should retrieve a category by ID")
    void testGetCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(sampleCategory));

        Category retrievedCategory = categoryService.getCategory(1L);

        assertThat(retrievedCategory).isNotNull();
        assertThat(retrievedCategory.getName()).isEqualTo("Dessert");
        verify(categoryRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Should retrieve all categories")
    void testGetAllCategories() {
        Category category2 = new Category();
        category2.setId(2L);
        category2.setName("Main Course");

        List<Category> categories = Arrays.asList(sampleCategory, category2);
        when(categoryRepository.findAll()).thenReturn(categories);

        List<Category> retrievedCategories = categoryService.getAllCategories();

        assertThat(retrievedCategories).hasSize(2);
        assertThat(retrievedCategories).extracting(Category::getName)
                .containsExactlyInAnyOrder("Dessert", "Main Course");
        verify(categoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Should delete a category by ID")
    void testRemoveCategory() {
        doNothing().when(categoryRepository).deleteById(1L);

        categoryService.removeCategory(1L);

        verify(categoryRepository, times(1)).deleteById(1L);
    }
}

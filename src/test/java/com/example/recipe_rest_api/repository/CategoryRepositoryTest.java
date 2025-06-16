package com.example.recipe_rest_api.repository;

import com.example.recipe_rest_api.model.Category; 
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestPropertySource(locations = "classpath:application-test.properties")
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository categoryRepository;

    @Test
    @DisplayName("Test saving and retrieving a Category")
    public void testSaveAndFindById() {
        // Arrange
        Category category = new Category();
        category.setName("Desserts");

        // Act
        Category savedCategory = categoryRepository.save(category);
        Optional<Category> retrievedCategory = categoryRepository.findById(savedCategory.getId());

        // Assert
        assertThat(retrievedCategory).isPresent();
        assertThat(retrievedCategory.get().getName()).isEqualTo("Desserts");
        assertEquals("Desserts", retrievedCategory.get().getName());
    }

    @Test
    @DisplayName("Test deleting a Category")
    public void testDeleteCategory() {
        // Arrange
        Category category = new Category();
        category.setName("Appetizers");
        Category savedCategory = categoryRepository.save(category);

        // Act
        categoryRepository.deleteById(savedCategory.getId());
        Optional<Category> deletedCategory = categoryRepository.findById(savedCategory.getId());

        // Assert
        assertThat(deletedCategory).isNotPresent();
    }
}

package com.example.recipe_rest_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.recipe_rest_api.model.Category;


@Repository
public interface CategoryRepository extends JpaRepository<Category, Long>{

}

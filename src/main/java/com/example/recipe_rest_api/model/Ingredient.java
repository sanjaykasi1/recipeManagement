package com.example.recipe_rest_api.model;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@Entity
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String Quantity;
	
	@ManyToOne
	@JoinColumn
	@JsonIgnore
	private Recipe recipe;

	public Ingredient() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Ingredient(String string, String string2, Recipe recipe2) {
		// TODO Auto-generated constructor stub
		this.name=string;
		this.Quantity=string2;
		this.recipe=recipe2;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getQuantity() {
		return Quantity;
	}

	public void setQuantity(String quantity) {
		Quantity = quantity;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	@Override
	public String toString() {
		return "name=" + name + ", Quantity=" + Quantity;
	}
	
	
	
}

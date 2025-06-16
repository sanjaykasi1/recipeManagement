package com.example.recipe_rest_api.model;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

@SuppressWarnings("unused")
@Entity
public class Recipe {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	
	private String description;
	
	private int time;
	
	@ManyToOne
	@JoinColumn

	private Category category;
	
	@OneToMany(mappedBy="recipe",cascade=CascadeType.ALL)
	private List<Ingredient> ingredients=new ArrayList<Ingredient>();

	public Recipe() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Recipe(String string, String string2) {
		// TODO Auto-generated constructor stub
		this.name=string;
		this.description=string2;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String Description) {
		this.description = Description;
	}

	public int getTime() {
		return time;
	}

	public void setTime(int string) {
		this.time = string;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public List<Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	@Override
	public String toString() {
		return "Recipe [id=" + id + ", name=" + name + ", description=" + description + ", time=" + time + "]";
	}
	
	
	
	
}

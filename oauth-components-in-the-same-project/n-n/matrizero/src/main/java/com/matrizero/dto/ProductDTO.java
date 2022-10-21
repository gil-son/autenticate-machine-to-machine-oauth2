package com.matrizero.dto;

import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import com.matrizero.entities.Category;
import com.matrizero.entities.Product;

public class ProductDTO implements Serializable{
    private static final long serialVersionUID = 1L;

    private Long id;

    @NotBlank(message = "Required field")
    @Size(min = 3, max = 50, message = "Fill the field with a minimum of 3 and a maximum of 50 characters")
    private String name;

    @NotBlank(message = "Required field")
    private String description;

    @Positive(message = "The value must be positive")
    private Double price;
    private String imgUrl;

    @PastOrPresent(message = "The date of product  no must be future")
    private Instant date; //moment

    private List<CategoryDTO> categories = new ArrayList<>();

    public ProductDTO() {}

    public ProductDTO(Long id, String name, String description, Double price, String imgUrl, Instant date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imgUrl = imgUrl;
        this.date = date;
    }


    public ProductDTO(Product entity) {
        this.id = entity.getId();
        this.name = entity.getName();
        this.description = entity.getDescription();
        this.price = entity.getPrice();
        this.imgUrl = entity.getImgUrl();
        this.date = entity.getDate();
    }


    public ProductDTO(Product entity, Set<Category> categories) {
        this(entity);
        categories.forEach(cat -> this.categories.add(new CategoryDTO(cat)));


		/*
		for(int i=0; i < this.categories.size(); i++) {
			Category category = new Category();
			this.categories.add(new CategoryDTO(category));
		}


		for(Category category : categories) {
			this.categories.add(new CategoryDTO(category));
		}
		*/

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

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Instant getDate() {
        return date;
    }

    public void setDate(Instant date) {
        this.date = date;
    }

    public List<CategoryDTO> getCategories() {
        return categories;
    }

    public void setCategories(List<CategoryDTO> categories) {
        this.categories = categories;
    }

}

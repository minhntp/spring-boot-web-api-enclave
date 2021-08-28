package com.nqminh.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotBlank
	@Size(min = 3, message = "Category name must have more than 3 characters")
	private String name;

	@Column(unique = true)
	@NotBlank
	private String slug;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	@JsonIgnore
	private List<Article> articles = new ArrayList<>();

	public Category() {
	}

	public Category(@NotBlank @Size(min = 3, message = "Category name must have more than 3 characters") String name) {
		this.name = name;
	}

	public Category(@NotBlank String name, @NotBlank String slug) {
		super();
		this.name = name;
		this.slug = slug;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSlug() {
		return slug;
	}

	public void setSlug(String slug) {
		this.slug = slug;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}

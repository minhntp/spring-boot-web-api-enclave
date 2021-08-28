package com.nqminh.api.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nqminh.api.requestbody.CategoryDetailsRequestBody;
import com.nqminh.api.responsebody.CategoryDetailsResponseBody;
import com.nqminh.api.service.CategoryService;
import com.nqminh.entity.Category;
import com.nqminh.util.RestUtils;
import com.nqminh.util.Utils;

@RestController
@RequestMapping("categories")
public class CategoryController {

	@Resource
	private CategoryService categoryService;

	@Autowired
	private Validator validator;

	@Autowired
	private Utils utils;

	@Autowired
	private RestUtils restUtils;

	// -------------------------------------------------------------------------

	// find all categories
	@GetMapping
	public ResponseEntity<Object> findAll() {

		List<CategoryDetailsResponseBody> response = categoryService.getAllCategories();

		return ResponseEntity
				.ok()
				.headers(restUtils.getHeaders(200, restUtils.SUCCESS))
				.body(response);
	}

	// create a new category: name
	@PostMapping
	public ResponseEntity<Object> create(@Valid @RequestBody CategoryDetailsRequestBody categoryRequestBody) {

		CategoryDetailsResponseBody response = categoryService.createCategory(categoryRequestBody);

		return ResponseEntity
				.status(HttpStatus.CREATED)
				.headers(restUtils.getHeaders(201, restUtils.CREATED))
				.body(response);
	}

	// update a category: name
	@PutMapping("/{slug}")
	public ResponseEntity<Object> update(@PathVariable String slug,
			@Valid @RequestBody CategoryDetailsRequestBody categoryRequestBody) {

		CategoryDetailsResponseBody response = categoryService.updateCategory(slug, categoryRequestBody);

		return ResponseEntity
				.ok()
				.headers(restUtils.getHeaders(200, restUtils.SUCCESS))
				.body(response);
	}

	@DeleteMapping("/{slug}")
	public ResponseEntity<Category> delete(@PathVariable String slug) {

		categoryService.deleteCategory(slug);

		return ResponseEntity
				.ok()
				.headers(restUtils.getHeaders(200, restUtils.SUCCESS))
				.build();
	}

}

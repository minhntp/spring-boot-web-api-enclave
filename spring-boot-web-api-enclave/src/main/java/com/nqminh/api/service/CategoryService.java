package com.nqminh.api.service;

import java.util.List;

import com.nqminh.api.requestbody.CategoryDetailsRequestBody;
import com.nqminh.api.responsebody.CategoryDetailsResponseBody;

public interface CategoryService {

	List<CategoryDetailsResponseBody> getAllCategories();

	CategoryDetailsResponseBody createCategory(CategoryDetailsRequestBody request);

	CategoryDetailsResponseBody updateCategory(String slug, CategoryDetailsRequestBody request);

	void deleteCategory(String slug);

}

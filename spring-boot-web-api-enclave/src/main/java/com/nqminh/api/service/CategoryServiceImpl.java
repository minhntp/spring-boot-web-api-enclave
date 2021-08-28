package com.nqminh.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqminh.api.exception.MyBadRequestException;
import com.nqminh.api.exception.MyNoDataException;
import com.nqminh.api.requestbody.CategoryDetailsRequestBody;
import com.nqminh.api.responsebody.CategoryDetailsResponseBody;
import com.nqminh.entity.Category;
import com.nqminh.repository.CategoryRepository;
import com.nqminh.util.Utils;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private Utils utils;

	@Override
	public List<CategoryDetailsResponseBody> getAllCategories() {

		List<Category> categories = categoryRepository.findAll();

		if (categories.isEmpty()) {
			throw new MyNoDataException();
		}

		return toResponseBody(categories);
	}

	@Override
	public CategoryDetailsResponseBody createCategory(CategoryDetailsRequestBody request) {

		// check if slug exists
		String slug = utils.generateSlug(request.getName());

		Optional<Category> foundCategory = categoryRepository.findBySlug(slug);

		if (!foundCategory.isEmpty()) {
			throw new MyBadRequestException("Category slug: " + slug + " already exists");
		}

		// save to database
		Category savedCategory = categoryRepository.save(toCategory(request));

		return toResponseBody(savedCategory);
	}

	@Override
	public CategoryDetailsResponseBody updateCategory(String slug, CategoryDetailsRequestBody request) {

		// check if slug exists
		Optional<Category> foundCategory = categoryRepository.findBySlug(slug);

		if (foundCategory.isEmpty()) {
			throw new MyBadRequestException("Category slug: " + slug + " not found");
		}

		// check new slug
		String newSlug = utils.generateSlug(request.getName());

		Optional<Category> checkNewSlug = categoryRepository.findBySlug(newSlug);

		if (!checkNewSlug.isEmpty()) {

			if (checkNewSlug.get().getId() != foundCategory.get().getId()) {
				throw new MyBadRequestException("Category slug: " + newSlug + " already exists");
			}
		}

		Category category = foundCategory.get();

		category.setName(request.getName().trim());
		category.setSlug(newSlug);

		Category savedCategory = categoryRepository.save(category);

		return toResponseBody(savedCategory);
	}

	@Override
	public void deleteCategory(String slug) {

		// check if slug exists
		Optional<Category> foundCategory = categoryRepository.findBySlug(slug);

		if (foundCategory.isEmpty()) {
			throw new MyBadRequestException("Category slug: " + slug + " not found");
		}

		//		// delete all articles from this category
		//		List<Integer> articleIds = new ArrayList<>();
		//		for (Article article : category.getArticles()) {
		//			articleIds.add(article.getId());
		//		}

		//		articleRepository.deleteAllById(articleIds);

		// delete this category (cascade all -> also delete all articles of this category)
		categoryRepository.delete(foundCategory.get());
	}
	// -------------------------------------------------------------------------

	private CategoryDetailsResponseBody toResponseBody(Category category) {

		// Category: id, name, slug

		// CategoryDetailsResponseBody: name, slug

		return new CategoryDetailsResponseBody(category.getName(), category.getSlug());
	}

	private List<CategoryDetailsResponseBody> toResponseBody(List<Category> categories) {

		List<CategoryDetailsResponseBody> response = new ArrayList<>();

		CategoryDetailsResponseBody temp;

		for (Category category : categories) {

			temp = new CategoryDetailsResponseBody(category.getName(), category.getSlug());

			response.add(temp);

		}
		return response;

	}

	private Category toCategory(CategoryDetailsRequestBody request) {

		// CategoryDetailsRequestBody: name

		// Category: id, name, slug

		String name = request.getName().trim();

		return new Category(name, utils.generateSlug(name));

	}

}

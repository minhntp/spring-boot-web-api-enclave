package com.nqminh.api.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nqminh.api.exception.MyBadRequestException;
import com.nqminh.api.exception.MyNoDataException;
import com.nqminh.api.exception.MyNotFoundException;
import com.nqminh.api.requestbody.ArticleDetailsRequestBody;
import com.nqminh.api.responsebody.ArticleDetailsResponseBody;
import com.nqminh.entity.Article;
import com.nqminh.entity.Category;
import com.nqminh.entity.User;
import com.nqminh.repository.ArticleRepository;
import com.nqminh.repository.CategoryRepository;
import com.nqminh.repository.UserRepository;
import com.nqminh.util.Utils;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private Utils utils;

	@Override
	public ArticleDetailsResponseBody getArticleBySlug(String slug) {

		try {

			Optional<Article> foundArticle = articleRepository.findBySlug(slug);

			return toArticleResponseBody(foundArticle.get());

		} catch (Exception e) {
			throw new MyNotFoundException("Article slug: " + slug + " not found");
		}

	}

	@Override
	public List<ArticleDetailsResponseBody> getArticlesByCategorySlug(String slug) {

		Optional<Category> foundCategory = categoryRepository.findBySlug(slug);

		if (foundCategory.isEmpty()) {
			throw new MyNotFoundException("Category slug: " + slug + " not found");
		}

		List<Article> articles = foundCategory.get().getArticles();

		if (articles.isEmpty()) {
			throw new MyNoDataException();
		}

		return toArticleResponseBody(articles);
	}

	@Override
	public ArticleDetailsResponseBody createArticle(ArticleDetailsRequestBody request) {

		// check user id
		Optional<User> foundUser = userRepository.findById(request.getUserId());

		if (foundUser.isEmpty()) {
			throw new MyBadRequestException("User id: " + request.getUserId() + " not found");
		}

		// check category slug
		Optional<Category> foundCategory = categoryRepository.findBySlug(request.getCategorySlug());

		if (foundCategory.isEmpty()) {
			throw new MyBadRequestException("Category slug: " + request.getCategorySlug() + " not found");
		}

		// check new article slug
		String newArticleSlug = utils.generateSlug(request.getTitle());

		Optional<Article> foundArticle = articleRepository.findBySlug(newArticleSlug);

		if (!foundArticle.isEmpty()) {
			throw new MyBadRequestException("Article slug: " + newArticleSlug + " already exists");
		}

		Article article = toArticle(request);

		article.setSlug(newArticleSlug);
		article.setUser(foundUser.get());
		article.setCategory(foundCategory.get());

		Article savedArticle = articleRepository.save(article);

		return toArticleResponseBody(savedArticle);
	}

	@Override
	public ArticleDetailsResponseBody updateArticle(ArticleDetailsRequestBody request, String articleSlug) {

		// check article slug
		Optional<Article> foundArticle = articleRepository.findBySlug(articleSlug);

		if (foundArticle.isEmpty()) {
			throw new MyBadRequestException("Article id: " + articleSlug + " not found");
		}

		// check user id
		Optional<User> foundUser = userRepository.findById(request.getUserId());

		if (foundUser.isEmpty()) {
			throw new MyBadRequestException("User id: " + request.getUserId() + " not found");
		}

		// check category slug
		Optional<Category> foundCategory = categoryRepository.findBySlug(request.getCategorySlug());

		if (foundCategory.isEmpty()) {
			throw new MyBadRequestException("Category slug: " + request.getCategorySlug() + " not found");
		}

		// check new article slug
		String newArticleSlug = utils.generateSlug(request.getTitle());

		Optional<Article> checkNewArticleSlug = articleRepository.findBySlug(newArticleSlug);

		if (!checkNewArticleSlug.isEmpty()) {

			if (checkNewArticleSlug.get().getId() != foundArticle.get().getId()) {

				throw new MyBadRequestException("Article slug: " + newArticleSlug + " already exists");

			}

		}

		Article article = foundArticle.get();

		// Article: id, slug, title, description, createdAt, modifiedAt, User, Category

		// ArticleDetailsRequestBody: userId, categoryId, title, description

		article.setUser(foundUser.get());
		article.setCategory(foundCategory.get());
		article.setTitle(request.getTitle().trim());
		article.setDescription(request.getDescription().trim());

		article.setSlug(newArticleSlug);
		article.setModifiedAt(new Date());

		System.out.println(getClass() + ": update article: ");

		Article savedArticle = articleRepository.save(article);

		return toArticleResponseBody(savedArticle);
	}

	@Override
	public void deleteArticleBySlug(String slug) {

		// check slug
		Optional<Article> foundArticle = articleRepository.findBySlug(slug);

		if (foundArticle.isEmpty()) {
			throw new MyBadRequestException("Article id: " + slug + " not found");
		}

		articleRepository.delete(foundArticle.get());
	}

	// -------------------------------------------------------------------------

	private ArticleDetailsResponseBody toArticleResponseBody(Article article) {

		// Article: id, slug, title, description, createdAt, modifiedAt, User, Category

		// ArticleDetailsResponseBody: slug, title, description, createdAt,
		// modifiedAt, userFullName, userRole, category

		ArticleDetailsResponseBody response = new ArticleDetailsResponseBody();

		BeanUtils.copyProperties(article, response);

		response.setUserFullName(article.getUser().getFullName());
		response.setUserRole(article.getUser().getRole().getName());
		response.setCategoryName(article.getCategory().getName());
		response.setCategorySlug(article.getCategory().getSlug());

		return response;
	}

	private List<ArticleDetailsResponseBody> toArticleResponseBody(List<Article> articles) {

		// Article: id, slug, title, description, createdAt, modifiedAt, User, Category

		// ArticleDetailsResponseBody: slug, title, description, createdAt,
		// modifiedAt, userFullName, userRole, category

		List<ArticleDetailsResponseBody> response = new ArrayList<>();

		ArticleDetailsResponseBody temp;

		for (Article article : articles) {

			temp = new ArticleDetailsResponseBody();

			BeanUtils.copyProperties(article, temp);

			temp.setUserFullName(article.getUser().getFullName());
			temp.setUserRole(article.getUser().getRole().getName());
			temp.setCategoryName(article.getCategory().getName());
			temp.setCategorySlug(article.getCategory().getSlug());

			response.add(temp);
		}

		return response;
	}

	private Article toArticle(ArticleDetailsRequestBody request) {

		// ArticleDetailsRequestBody: userId, categoryId, title, description

		// Article: id, slug, title, description, createdAt, modifiedAt, User, Category

		Article article = new Article();

		article.setTitle(request.getTitle().trim());
		article.setDescription(request.getDescription().trim());

		article.setCreatedAt(new Date());
		article.setModifiedAt(new Date());

		return article;
	}

}

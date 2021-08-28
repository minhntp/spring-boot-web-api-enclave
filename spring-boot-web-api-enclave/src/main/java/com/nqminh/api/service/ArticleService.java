package com.nqminh.api.service;

import java.util.List;

import com.nqminh.api.requestbody.ArticleDetailsRequestBody;
import com.nqminh.api.responsebody.ArticleDetailsResponseBody;

public interface ArticleService {

	ArticleDetailsResponseBody getArticleBySlug(String slug);

	List<ArticleDetailsResponseBody> getArticlesByCategorySlug(String slug);

	ArticleDetailsResponseBody createArticle(ArticleDetailsRequestBody request);

	ArticleDetailsResponseBody updateArticle(ArticleDetailsRequestBody request, String articleSlug);

	void deleteArticleBySlug(String slug);

}

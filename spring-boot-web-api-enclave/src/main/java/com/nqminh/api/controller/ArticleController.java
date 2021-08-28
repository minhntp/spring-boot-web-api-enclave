package com.nqminh.api.controller;

import java.net.URI;
import java.util.List;

import javax.annotation.Resource;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.nqminh.api.requestbody.ArticleDetailsRequestBody;
import com.nqminh.api.responsebody.ArticleDetailsResponseBody;
import com.nqminh.api.service.ArticleService;
import com.nqminh.entity.Article;
import com.nqminh.util.RestUtils;

@RestController
@RequestMapping("articles")
public class ArticleController {

	@Autowired
	private RestUtils restUtils;

	@Resource
	private ArticleService articleService;

	// -------------------------------------------------------------------------

	// find 1 article by slug
	@GetMapping("/{slug}")
	public ResponseEntity<Object> getArticleBySlug(@PathVariable String slug) {

		ArticleDetailsResponseBody response = articleService.getArticleBySlug(slug);

		return ResponseEntity
				.ok()
				.headers(restUtils.getHeaders(200, restUtils.SUCCESS))
				.body(response);
	}

	// find all by category slug
	@GetMapping("/category/{slug}")
	public ResponseEntity<Object> getArticlesByCategorySlug(@PathVariable String slug) {

		List<ArticleDetailsResponseBody> response = articleService.getArticlesByCategorySlug(slug);

		return ResponseEntity
				.ok()
				.headers(restUtils.getHeaders(200, restUtils.SUCCESS))
				.body(response);
	}

	// create a new article: userId, categorySlug, title, description
	@PostMapping
	public ResponseEntity<Object> createArticle(@Valid @RequestBody ArticleDetailsRequestBody articleRequestBody) {

		ArticleDetailsResponseBody response = articleService.createArticle(articleRequestBody);

		// build Uri to access created article
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{slug}")
				.buildAndExpand(response.getSlug())
				.toUri();

		return ResponseEntity
				.created(location)
				.headers(restUtils.getHeaders(201, restUtils.CREATED))
				.body(response);
	}

	// update an article by slug: userId, categoryId, title, description(opt)
	@PutMapping("/{slug}")
	public ResponseEntity<Object> updateArticle(@PathVariable String slug,
			@Valid @RequestBody ArticleDetailsRequestBody articleRequestBody) {

		ArticleDetailsResponseBody response = articleService.updateArticle(articleRequestBody, slug);

		// build Uri to access created article
		URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{slug}")
				.buildAndExpand(response.getSlug())
				.toUri();

		HttpHeaders headers = new HttpHeaders();

		headers.add(restUtils.CODE, Integer.toString(200));
		headers.add(restUtils.MESSAGE, restUtils.SUCCESS);
		headers.add(restUtils.LOCATION, location.toString());

		return ResponseEntity
				.ok()
				.headers(headers)
				.body(response);
	}

	@DeleteMapping("/{slug}")
	public ResponseEntity<Article> delete(@PathVariable String slug) {

		articleService.deleteArticleBySlug(slug);

		return ResponseEntity
				.ok()
				.headers(restUtils.getHeaders(200, restUtils.SUCCESS))
				.build();
	}

}

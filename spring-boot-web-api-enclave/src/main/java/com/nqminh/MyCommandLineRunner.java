package com.nqminh;

import java.util.Date;
import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.nqminh.entity.Article;
import com.nqminh.entity.Category;
import com.nqminh.entity.User;
import com.nqminh.repository.ArticleRepository;
import com.nqminh.repository.CategoryRepository;
import com.nqminh.repository.UserRepository;
import com.nqminh.util.Utils;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ArticleRepository articleRepository;

	@Autowired
	private CategoryRepository categoryRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private Utils utils;

	@Override
	public void run(String... args) throws Exception {
		// encodeAllPasswords();
		// slugifyAllNames();
		// addDateForArticles();
	}

	private void encodeAllPasswords() {
		Iterable<User> users = userRepository.findAll();

		Iterator<User> iterator = users.iterator();

		while (iterator.hasNext()) {
			User user = iterator.next();

			String encodedPassword = passwordEncoder.encode(user.getEncyptedPassword());

			user.setEncyptedPassword(encodedPassword);
		}

		userRepository.saveAll(users);
	}

	private void slugifyAllNames() {
		// articles
		Iterable<Article> articles = articleRepository.findAll();

		Iterator<Article> articlesIterator = articles.iterator();

		while (articlesIterator.hasNext()) {
			Article article = articlesIterator.next();

			article.setSlug(utils.generateSlug(article.getTitle()));
		}

		articleRepository.saveAll(articles);

		// categories
		Iterable<Category> categories = categoryRepository.findAll();

		Iterator<Category> categoriesIterator = categories.iterator();

		while (categoriesIterator.hasNext()) {
			Category category = categoriesIterator.next();

			category.setSlug(utils.generateSlug(category.getName()));
		}

		categoryRepository.saveAll(categories);
	}

	private void addDateForArticles() {
		Iterable<Article> articles = articleRepository.findAll();

		Iterator<Article> articlesIterator = articles.iterator();

		while (articlesIterator.hasNext()) {
			Article article = articlesIterator.next();

			article.setCreatedAt(new Date());
			article.setModifiedAt(new Date());
		}

		articleRepository.saveAll(articles);
	}
}

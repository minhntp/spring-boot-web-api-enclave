package com.nqminh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nqminh.entity.Article;
import com.nqminh.entity.Category;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Integer> {

	Optional<Article> findBySlug(String slug);

	List<Article> findByCategory(Category category);
}

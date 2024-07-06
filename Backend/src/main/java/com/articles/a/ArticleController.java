package com.articles.a;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/books")
@CrossOrigin(origins = "http://localhost:8080")
public class ArticleController {

    private final ArticleRepository articleRepository;

    @Autowired
    public ArticleController(ArticleRepository articleRepository) {
        this.articleRepository = articleRepository;
    }

    @GetMapping("/getAll")
    public ResponseEntity<Iterable<Article>> getAllBooks() {
        Iterable<Article> articles = articleRepository.findAll();
        return ResponseEntity.ok(articles);
    }

    @PostMapping
    public ResponseEntity<Article> addArticle(@RequestBody Article article) {
        article.setTime(LocalDateTime.now()); // Установите текущее время
        Article savedArticle = articleRepository.save(article);
        return ResponseEntity.ok(savedArticle);
    }
}

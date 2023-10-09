package course.spring.intro.dom;

import course.spring.intro.model.Article;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class RepositoryArticleInMemory {
    AtomicLong nextId = new AtomicLong();


    private ConcurrentHashMap<Long, Article> articles = new ConcurrentHashMap<>();

    public RepositoryArticleInMemory() {
        this(List.of(new Article(1L, "First", "Test", Collections.emptySet())));
    }

    public List<Article> findAll() {
        return new ArrayList<>(articles.values());
    }

    public Optional<Article> findById(Long id) {
        return Optional.ofNullable(articles.get(id));
    }

    public Article create(Article article) {
        article.setId(nextId.incrementAndGet());
        articles.put(article.getId(), article);
        return article;
    }

    public RepositoryArticleInMemory(List<Article> initialArticles) {
        initialArticles.forEach(art -> articles.put(art.getId(), art));
    }
}

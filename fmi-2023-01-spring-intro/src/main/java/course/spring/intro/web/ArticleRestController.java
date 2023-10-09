package course.spring.intro.web;

import course.spring.intro.dom.RepositoryArticleInMemory;
import course.spring.intro.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleRestController {
    @Autowired
    private RepositoryArticleInMemory articleRepository;

    @GetMapping
    public List<Article> getArticles() {
        return articleRepository.findAll();
    }
}

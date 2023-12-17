package course.spring.web;

import course.spring.model.Article;
import course.spring.provider.ArticleProvider;
import course.spring.qualifiers.Default;
import jakarta.inject.Inject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class AriclesController {
    //    @Qualifier("defaultProvider")
    @Inject
    @Default
    private ArticleProvider articleProvider;


    @GetMapping
    List<Article> getAllArticles() {
        return articleProvider.getArticles();
    }
}

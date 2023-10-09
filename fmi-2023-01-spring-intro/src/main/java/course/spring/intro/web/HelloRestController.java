package course.spring.intro.web;

import course.spring.intro.dom.RepositoryArticleInMemory;
import course.spring.intro.model.Article;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@RequestMapping("/api/hello")
public class HelloRestController {


    @GetMapping
    public String sayHello() {
        return "Hello from Spring MVC!";
    }

    @GetMapping("/article")
    public Article getArticle() {
        return new Article(1L, "Spring Intro", "Spring Boot project demo. Spring is backend friendly.", Set.of("Spring", "Boot"));
    }

}

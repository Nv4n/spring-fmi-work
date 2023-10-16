package course.spring.intro.init;

import course.spring.intro.dao.ArticleRepositoryInMemory;
import course.spring.intro.model.Article;
import org.springframework.boot.CommandLineRunner;

public class DataInitializer implements CommandLineRunner {
    private ArticleRepositoryInMemory repo;

    public DataInitializer(ArticleRepositoryInMemory repo) {
        this.repo = repo;
    }

    @Override
    public void run(String... args) throws Exception {
        repo.create(new Article(1L, "Spring Intro", "Spring is developer friendly web service platform", "T. Iliev"));
    }
}

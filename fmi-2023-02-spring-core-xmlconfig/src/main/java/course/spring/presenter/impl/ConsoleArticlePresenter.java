package course.spring.presenter.impl;

import course.spring.presenter.ArticlePresenter;
import course.spring.provider.ArticleProvider;

public class ConsoleArticlePresenter implements ArticlePresenter {
    private ArticleProvider provider;

    public ConsoleArticlePresenter(ArticleProvider provider) {
        this.provider = provider;
    }

    @Override
    public void present() {
        provider.getArticles().forEach(System.out::println);
    }
}

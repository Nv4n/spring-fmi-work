package course.spring.intro.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Article {
    @NonNull
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String content;

    private Set<String> keywords = Set.of();
    private LocalDateTime creaded = LocalDateTime.now();
    private LocalDateTime modified = LocalDateTime.now();

    public Article(Long id, String title, String content, Set<String> keywords) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.keywords = keywords;
    }
}

package com.example.exam01practice.model;

import lombok.*;

import java.util.Set;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Post implements Identifiable<Long> {
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String title;
    @NonNull
    private String body;
    @NonNull
    private User author;
    @NonNull
    private Set<String> tags;
    @NonNull
    private Integer reactions;
}

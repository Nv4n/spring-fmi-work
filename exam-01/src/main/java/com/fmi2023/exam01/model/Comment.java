package com.fmi2023.exam01.model;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Comment implements Identifiable<Long> {
    @EqualsAndHashCode.Include
    private Long Id;
    private String pageUrl;
    private String authorEmail;
    private String commentText;
}

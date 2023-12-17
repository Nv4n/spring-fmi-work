package com.example.exam01practice.model;

import lombok.*;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User implements Identifiable<Long> {
    @EqualsAndHashCode.Include
    private Long id;
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private Gender gender;
}

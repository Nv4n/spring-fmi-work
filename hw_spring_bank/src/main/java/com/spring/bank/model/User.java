package com.spring.bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NonNull
    @Size(min = 5, max = 20)
    @Column(unique = true, nullable = false, length = 40)
    @EqualsAndHashCode.Include
    private String username;

    @NotNull
    @Column(length = 64)
    @Size(min = 8, max = 32)
    private String passwordHash;

    @OneToMany(mappedBy = "owner")
    @JsonIgnore
    @ToString.Exclude
    private List<BankAccount> accounts = new ArrayList<>();

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();

    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Temporal(TemporalType.TIMESTAMP)
    private Date modifiedAt = new Date();

}

package com.spring.bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import org.iban4j.CountryCode;
import org.iban4j.Iban;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "bankacounts")
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NotNull
    @EqualsAndHashCode.Include
    private String iban = Iban.random(CountryCode.BG).toString();

    @NotNull
    @PositiveOrZero
    private Double balance;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @JsonIgnore
    private User owner;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private UUID ownerId;

    @OneToMany(mappedBy = "account")
    @ToString.Exclude
    @JsonIgnore
    private List<Transaction> trasactions = new ArrayList<>();

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

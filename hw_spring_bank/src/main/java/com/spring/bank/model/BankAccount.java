package com.spring.bank.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.iban4j.CountryCode;
import org.iban4j.Iban;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "bankacounts", uniqueConstraints = @UniqueConstraint(name = "UC_NicknameUser", columnNames = {"nickname", "owner_id"}))
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @EqualsAndHashCode.Include
    private String iban = Iban.random(CountryCode.BG).toString();

    @NotNull
    @Size(min = 3, max = 20)
    @Column(nullable = false, length = 40)
    private String nickname;

    @NotNull
    @PositiveOrZero
    private Double balance = 0.0;

    @ManyToOne(cascade = CascadeType.MERGE)
    @ToString.Exclude
    @JsonIgnore
    private User owner;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private UUID ownerId;

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

package com.spring.bank.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Table(name = "transactions")
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @NotNull
    @Positive
    @Max(10000)
    private Double amount;
    
    @NotNull
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate = new Date();

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @JsonIgnore
    private BankAccount sender;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private UUID senderId;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @ToString.Exclude
    @JsonIgnore
    private BankAccount receiver;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Transient
    private UUID receiverId;
}
package com.fmi2023.exam01.model;

public interface Identifiable<K> {
    K getId();

    void setId(K id);
}

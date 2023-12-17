package com.example.exam01practice.model;


public interface Identifiable<K> {
    K getId();

    void setId(K id);
}

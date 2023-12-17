package com.example.exam01practice.dao;

public interface IdGeneratorFactory<K> {
    IdGenerator<K> createIdGenerator();
}

package com.fmi2023.exam01.dao;

public interface IdGenFactory<K> {
    IdGenerator<K> createIdGenerator();
}

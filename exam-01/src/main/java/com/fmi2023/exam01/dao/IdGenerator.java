package com.fmi2023.exam01.dao;

public interface IdGenerator<K> {
    K getNextId();
}

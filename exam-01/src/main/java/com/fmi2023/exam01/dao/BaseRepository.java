package com.fmi2023.exam01.dao;

import com.fmi2023.exam01.model.Identifiable;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<K, V extends Identifiable<K>> {
    List<V> findAll();

    Optional<V> findById(K id);

    V create(V entity);

    Integer getEntityCount();
}

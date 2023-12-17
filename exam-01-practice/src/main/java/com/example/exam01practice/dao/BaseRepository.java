package com.example.exam01practice.dao;

import com.example.exam01practice.model.Identifiable;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface BaseRepository<K, V extends Identifiable<K>> {
    List<V> findAll();

    Optional<V> findById(K id);

    V create(V entity);

    Optional<V> update(V entity);

    Optional<V> deleteById(K id);
}

package com.example.exam01practice.dao.impl;

import com.example.exam01practice.dao.BaseRepository;
import com.example.exam01practice.dao.IdGenerator;
import com.example.exam01practice.model.Identifiable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BaseRepoImpl<K, V extends Identifiable<K>> implements BaseRepository<K, V> {
    private final Map<K, V> entities = new ConcurrentHashMap<K, V>();
    private final IdGenerator<K> idGenerator;

    public BaseRepoImpl(IdGenerator<K> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public List<V> findAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public Optional<V> findById(K id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public V create(V entity) {
        entity.setId(idGenerator.getNextId());
        entities.put(entity.getId(), entity);
        return entity;
    }

    @Override
    public Optional<V> update(V entity) {
        return Optional.ofNullable(entities.put(entity.getId(), entity));
    }

    @Override
    public Optional<V> deleteById(K id) {
        return Optional.ofNullable(entities.remove(id));
    }
}

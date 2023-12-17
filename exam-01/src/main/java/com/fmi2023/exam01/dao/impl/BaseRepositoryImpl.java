package com.fmi2023.exam01.dao.impl;

import com.fmi2023.exam01.dao.BaseRepository;
import com.fmi2023.exam01.dao.IdGenerator;
import com.fmi2023.exam01.model.Identifiable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class BaseRepositoryImpl<K, V extends Identifiable<K>> implements BaseRepository<K, V> {
    private Map<K, V> entities = new ConcurrentHashMap<>();
    private IdGenerator<K> idGenerator;

    public BaseRepositoryImpl(IdGenerator<K> idGenerator) {
        this.idGenerator = idGenerator;
    }

    @Override
    public List<V> findAll() {
        return new ArrayList<V>(entities.values());
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
    public Integer getEntityCount() {
        return entities.size();
    }
}

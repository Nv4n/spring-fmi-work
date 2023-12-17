package com.example.exam01practice.dao.impl;

import com.example.exam01practice.dao.IdGenerator;
import com.example.exam01practice.dao.IdGeneratorFactory;
import org.springframework.stereotype.Component;

@Component
public class LongIdGenFactory implements IdGeneratorFactory<Long> {
    @Override
    public IdGenerator<Long> createIdGenerator() {
        return new LongIdGen();
    }
}

package com.fmi2023.exam01.dao.impl;

import com.fmi2023.exam01.dao.IdGenFactory;
import com.fmi2023.exam01.dao.IdGenerator;
import org.springframework.stereotype.Component;

@Component
public class LongIdGenFactoryImpl implements IdGenFactory<Long> {
    @Override
    public IdGenerator<Long> createIdGenerator() {
        return new LongIdGenImpl();
    }
}

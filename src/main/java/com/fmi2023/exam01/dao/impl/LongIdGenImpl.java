package com.fmi2023.exam01.dao.impl;

import com.fmi2023.exam01.dao.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class LongIdGenImpl implements IdGenerator<Long> {
    private AtomicLong nextId = new AtomicLong();

    @Override
    public Long getNextId() {
        return nextId.incrementAndGet();
    }
}

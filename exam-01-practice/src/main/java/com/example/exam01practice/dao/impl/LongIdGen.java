package com.example.exam01practice.dao.impl;

import com.example.exam01practice.dao.IdGenerator;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicLong;

@Component
public class LongIdGen implements IdGenerator<Long> {
    private final AtomicLong nextId = new AtomicLong();

    @Override
    public Long getNextId() {
        return nextId.incrementAndGet();
    }
}

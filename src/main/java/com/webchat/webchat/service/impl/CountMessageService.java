package com.webchat.webchat.service.impl;

import com.webchat.webchat.entities.CountMessage;
import com.webchat.webchat.repository.CountMessageRepository;
import com.webchat.webchat.service.ICountMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountMessageService implements ICountMessageService {
    @Autowired
    private CountMessageRepository repo;

    @Override
    public Integer count(String month, String year) {
        List<CountMessage> count = repo.count(month, year);
        return count.isEmpty() ? 0 : count.get(0).getQuantity();
    }
}

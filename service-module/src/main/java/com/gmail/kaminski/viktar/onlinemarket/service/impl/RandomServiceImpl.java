package com.gmail.kaminski.viktar.onlinemarket.service.impl;

import com.gmail.kaminski.viktar.onlinemarket.service.RandomService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class RandomServiceImpl implements RandomService {
    @Value("${custom.password.pattern}")
    private String passwordPattern;
    private Random random = new Random();

    @Override
    public String getLatinsAndNumbers(int amountSymbols) {
        StringBuilder builder = new StringBuilder();
        while (amountSymbols-- != 0) {
            int character = (int) (Math.random() * passwordPattern.length());
            builder.append(passwordPattern.charAt(character));
        }
        return builder.toString();
    }

    @Override
    public int get(int start, int stop) {
        return random.nextInt(stop - start + 1) + start;
    }
}

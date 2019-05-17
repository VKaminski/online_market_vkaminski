package com.gmail.kaminski.viktar.onlinemarket.service;

public interface RandomService {
    String getLatinsAndNumbers(int amountSymbols);

    int get(int start, int stop);
}

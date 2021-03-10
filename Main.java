package com.AITU;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

public class Main {

    public static void main(String[] args) throws TelegramApiRequestException {

        System.out.println("Bot starting...");
        new FileReader();

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();
        botsApi.registerBot(new ScheduleBot());
        System.out.println("Bot started!");
    }
}

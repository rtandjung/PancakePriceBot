/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rtandjung.pancakepricebot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;

/**
 *
 * @author rizki
 */
public class BotStarter {

    public static void main(String[] args) throws TelegramApiRequestException {
        ApiContextInitializer.init();

        TelegramBotsApi telegramBotsApi = new TelegramBotsApi();
        
        BotPolling polbot = new BotPolling();
        telegramBotsApi.registerBot(polbot);
        System.out.println("Robot Started");
    }
}

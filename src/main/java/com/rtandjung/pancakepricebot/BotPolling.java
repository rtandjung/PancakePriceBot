/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rtandjung.pancakepricebot;

import com.rtandjung.pancakepricebot.priceapi.PriceModel;
import com.rtandjung.pancakepricebot.priceapi.SwapPricing;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 *
 * @author rizki
 */
public class BotPolling extends TelegramLongPollingBot {

    private static final String BOT_USERNAME = "usernamebot";
    private static final String BOT_TOKEN = "1333611111:YOURBOTTOKENHERE";
    private static final String BOT_PREFIX = "/";

    private static final String CAKE = "0x0e09fabb73bd3ade0a17ecc321fd13a19e81ce82";
    private static final String BUSD = "0xe9e7CEA3DedcA5984780Bafc599bD69ADd087D56";
    private static final String WBNB = "0xbb4CdB9CBd36B01bD1cBaEBF2De08d9173bc095c";
    private static final String SYRUP = "0x009cF7bC57584b7998236eff51b98A168DceA9B0";
    private static final String WBNBCAKE = "0xA527a61703D82139F8a06Bc30097cC9CAA2df5A6";
    private static final String WBNBBUSD = "0x1B96B92314C44b159149f7E0303511fB2Fc4774f";
    private static final String WBNBSYRUP = "0x52fa46D17DcDA5c512cA09b6B48a8fc69EF5B50F";

    private SwapPricing swp;

    public BotPolling() {
        swp = new SwapPricing();

    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        updateRecv(update);
    }

    @Override
    public String getBotUsername() {
        return BOT_USERNAME;
    }

    private void updateRecv(Update update) {
        if (update.hasMessage()) {
            Message message = update.getMessage();
            String chatid = message.getChatId().toString();
            //only read text message
            if (message.hasText()) {
                String textMsg = message.getText().toLowerCase().replace("@" + BOT_USERNAME, "");

                if (textMsg.startsWith(BOT_PREFIX + "cake")) {
                    //call cake price
                    PriceModel wbnbBusdP = swp.checkPrice(WBNBBUSD, WBNB, BUSD);
                    PriceModel wbnbCakeP = swp.checkPrice(WBNBCAKE, WBNB, CAKE);
                    String price = "*CAKE Price*\n"
                            + wbnbCakeP.getPriceA() + " " + wbnbCakeP.getPairA() + "\n"
                            + (wbnbCakeP.getPriceA() * wbnbBusdP.getPriceB()) + " " + wbnbBusdP.getPairB() + "";
                    sendMessageBuild(chatid, price);
                } else if (textMsg.startsWith(BOT_PREFIX + "help")) {
                    sendMessageBuild(chatid, "use /cake /price command to check price, next update coming soon. dont forget to /donate");
                } else if (textMsg.startsWith(BOT_PREFIX + "price")) {
                    //call price, you can use this for /p COINAME

                    //for excample im using cake price
                    PriceModel wbnbBusdP = swp.checkPrice(WBNBBUSD, WBNB, BUSD);
                    PriceModel wbnbCakeP = swp.checkPrice(WBNBCAKE, WBNB, CAKE);
                    String price = "*CAKE Price*\n"
                            + wbnbCakeP.getPriceA() + " " + wbnbCakeP.getPairA() + "\n"
                            + (wbnbCakeP.getPriceA() * wbnbBusdP.getPriceB()) + " " + wbnbBusdP.getPairB() + "";
                    sendMessageBuild(chatid, price);

                } else if (textMsg.startsWith(BOT_PREFIX + "donate")) {
                    sendMessageBuild(chatid, "Send dev some cake to support  : 0x80b12e98cb283df9f23a9307fb8ba5d938cfc11b");
                } else if (textMsg.startsWith(BOT_PREFIX)) {
                    //default command
                    sendMessageBuild(chatid, "Invalid Command /help for help. contact @rtandjung if you have any idea for this bot");
                }

            }
        }
    }

    private void sendMessageBuild(String chatid, String txt) {
        SendMessage sMsg = new SendMessage();
        sMsg.setChatId(chatid);
        sMsg.setText(txt);
        sMsg.enableMarkdown(true);
        sMsg.disableWebPagePreview();
        try {
            execute(sMsg);
        } catch (TelegramApiException ex) {
            Logger.getLogger(BotPolling.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

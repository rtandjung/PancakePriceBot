/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rtandjung.pancakepricebot.priceapi;

import java.math.BigInteger;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.web3j.contracts.eip20.generated.ERC20;
import org.web3j.crypto.Credentials;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.tx.gas.DefaultGasProvider;

/**
 *
 * @author rizki
 */
public class SwapPricing {

    Web3j web3;
    Credentials credentials;

    public SwapPricing() {

        // Account address: 0x1583c05d6304b6651a7d9d723a5c32830f53a12f
        HttpService https = new HttpService("https://bsc-dataseed1.defibit.io/");
        web3 = Web3j.build(https);  // defaults to http://localhost:8545/
        credentials = Credentials.create("11111111111111111111111111111111111111111111111111111111111111111");

    }


    public PriceModel checkPrice(String contract, String paira, String pairb) {
        PriceModel pm = new PriceModel("A", "B", 0, 0);
        try {

            ERC20 tokenAContract = ERC20.load(paira, web3, credentials, new DefaultGasProvider());

            ERC20 tokenBContract = ERC20.load(pairb, web3, credentials, new DefaultGasProvider());

            BigInteger balanceA = tokenAContract.balanceOf(contract).send();
            BigInteger balanceB = tokenBContract.balanceOf(contract).send();

            String tickerA = tokenAContract.symbol().send();
            String tickerB = tokenBContract.symbol().send();

            BigInteger aDecimal = BigInteger.valueOf(Long.parseLong(buildDecimal(tokenAContract.decimals().send().toString())));
            BigInteger bDecimal = BigInteger.valueOf(Long.parseLong(buildDecimal(tokenBContract.decimals().send().toString())));
            float pricea = (balanceA.floatValue() / aDecimal.floatValue()) / (balanceB.floatValue() / bDecimal.floatValue());
            float priceb = (balanceB.floatValue() / bDecimal.floatValue()) / (balanceA.floatValue() / aDecimal.floatValue());
           
            pm = new PriceModel(tickerA, tickerB, pricea, priceb);

        } catch (Exception ex) {
            Logger.getLogger(SwapPricing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pm;
    }

    public String buildDecimal(String zerodigit) {
        String ret = "1";
        int x = Integer.parseInt(zerodigit);
        for (int i = 0; i < x; i++) {
            ret = ret + "0";
        }

        return ret;
    }

}

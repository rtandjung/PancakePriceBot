/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.rtandjung.pancakepricebot.priceapi;

/**
 *
 * @author rizki
 */
public class PriceModel {

    private String pairA;
    private String pairB;
    private float priceA;
    private float priceB;

    public PriceModel() {
    }

    public PriceModel(String pairA, String pairB, float priceA, float priceB) {
        this.pairA = pairA;
        this.pairB = pairB;
        this.priceA = priceA;
        this.priceB = priceB;
    }

    public String getPairA() {
        return pairA;
    }

    public void setPairA(String pairA) {
        this.pairA = pairA;
    }

    public String getPairB() {
        return pairB;
    }

    public void setPairB(String pairB) {
        this.pairB = pairB;
    }

    public float getPriceA() {
        return priceA;
    }

    public void setPriceA(float priceA) {
        this.priceA = priceA;
    }

    public float getPriceB() {
        return priceB;
    }

    public void setPriceB(float priceB) {
        this.priceB = priceB;
    }

}

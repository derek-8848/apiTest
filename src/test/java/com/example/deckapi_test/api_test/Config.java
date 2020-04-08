package com.example.deckapi_test.api_test;

import io.restassured.RestAssured;

public class Config {
    public static final String BaseDeckURI="https://deckofcardsapi.com/api/deck/";
    public static final String NewDeckURI="https://deckofcardsapi.com/api/deck/new/";
    public static String DrawDeckURI(String deck_id){
        StringBuilder sb = new StringBuilder(BaseDeckURI);
        if(deck_id==null || deck_id.length()==0){
            sb.append("new");
        }else{
            sb.append(deck_id);
        }
        sb.append("/draw/");
        return sb.toString();
    }
    public static String getDeckId(){
        return RestAssured.given().baseUri(Config.NewDeckURI).get().jsonPath().get("deck_id");
    }
    public static int DeckWithJoker=54;
    public static int DeckWithoutJoker=52;
}

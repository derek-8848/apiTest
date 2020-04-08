package com.example.deckapi_test.api_test;

import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Test;

public class DrawCardFromDeckAPITest{

    public DrawCardFromDeckAPITest(){}

//    @Override
////    @Test
//    public void runAllTest() {
//        DrawNewDeck(null,2);
//        DrawNewDeck(null,-1);
//        DrawNewDeck(null,53);
//        DrawNewDeck(Config.getDeckId(),2);
//        DrawNewDeck(Config.getDeckId(),-1);
//        DrawNewDeck(Config.getDeckId(),53);
//        DrawLoop(Config.getDeckId(),3,5);
//    }

    private void DrawNewDeck(String deck_id,int drawCount){
        if(drawCount>Config.DeckWithoutJoker){
            DrawNewDeckOverflow(deck_id,drawCount);
        }else if(drawCount<0){
            DrawNewDeckCards(deck_id,Config.DeckWithoutJoker+drawCount);
        }else{
            DrawNewDeckCards(deck_id,drawCount);
        }
    }

    @Test
    public void DrawNewDeckTest1(){
        DrawNewDeck(null,2);
    }

    @Test
    public void DrawNewDeckTest2(){
        DrawNewDeck(null,-1);
    }

    @Test
    public void DrawNewDeckTest3(){
        DrawNewDeck(null,53);
    }

    @Test
    public void DrawNewDeckTest4(){
        DrawNewDeck(Config.getDeckId(),2);
    }

    @Test
    public void DrawNewDeckTest5(){
        DrawNewDeck(Config.getDeckId(),-1);
    }

    @Test
    public void DrawNewDeckTest6(){
        DrawNewDeck(Config.getDeckId(),53);
    }

    @Test
    public void DrawNewDeckTest7(){
        DrawLoop(Config.getDeckId(),3,5);
    }

    //if draw count is greater a new deck card number
    private void DrawNewDeckOverflow(String deck_id,int drawCount){
        if(drawCount>Config.DeckWithoutJoker){
            RestAssured.given()
                    .baseUri(Config.DrawDeckURI(deck_id))
                    .queryParam("count",drawCount)
                    .get()
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .body("success",Matchers.equalTo(false))
                    .body("remaining",Matchers.equalTo(0))
                    .body("error",Matchers.notNullValue());
        }
    }

    //draw cards from a deck iteratively
    private void DrawLoop(String deck_id,int drawCount,int loop){
        for(int i=0;i<loop;i++){
            DrawRemainCards(deck_id,drawCount);
        }
    }

    //draw cards from a new deck
    private void DrawNewDeckCards(String deck_id,int drawCount){
        if(drawCount<=Config.DeckWithoutJoker){
            int result = Config.DeckWithoutJoker-drawCount;
            RestAssured.given()
                    .baseUri(Config.DrawDeckURI(deck_id))
                    .queryParam("count",drawCount)
                    .get()
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .body("success",Matchers.equalTo(true))
                    .body("cards",Matchers.hasSize(drawCount))
                    .body("remaining",Matchers.equalTo(result));
        }
    }

    //draw cards from the existing deck
    private void DrawRemainCards(String deck_id,int drawCount){
        int remaining = RestAssured.given().baseUri(Config.DrawDeckURI(deck_id)).get().jsonPath().get("remaining");

        if(drawCount<=Config.DeckWithoutJoker){
            int result = remaining-drawCount;
            RestAssured.given()
                    .baseUri(Config.DrawDeckURI(deck_id))
                    .queryParam("count",drawCount)
                    .get()
                    .then()
                    .assertThat()
                    .statusCode(200)
                    .body("success",Matchers.equalTo(true))
                    .body("remaining",Matchers.equalTo(result));
        }
    }
}

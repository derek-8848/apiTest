package com.example.deckapi_test.api_test;


import io.restassured.RestAssured;
import org.hamcrest.Matchers;
import org.junit.Test;

public class NewDeckAPITest{

    @Test
    public void getDeckWithJoker(){
        RestAssured.given()
                .baseUri(Config.NewDeckURI)
                .queryParam("jokers_enabled",true)
                .get()
                .then()
                .assertThat()
                .statusCode(200)
                .body("remaining",Matchers.equalTo(54));
    }

    @Test
    public void getDeckWithoutJoker(){
        RestAssured.given()
                .baseUri(Config.NewDeckURI)
                .get()
                .then()
                .assertThat()
                .statusCode(200)
                .body("remaining", Matchers.equalTo(52));
    }

    @Test
    public void postDeckWithJoker(){
        RestAssured.given()
                .get("https://deckofcardsapi.com/api/deck/new?jokers_enabled=true")
                .then()
                .assertThat()
                .statusCode(200);
    }
}

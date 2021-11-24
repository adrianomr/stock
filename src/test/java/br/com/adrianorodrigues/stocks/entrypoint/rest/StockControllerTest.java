package br.com.adrianorodrigues.stocks.entrypoint.rest;

import br.com.adrianorodrigues.stocks.configuration.TestRedisConfiguration;
import br.com.adrianorodrigues.stocks.domain.Stock;
import br.com.adrianorodrigues.stocks.exceptions.StockDataNotFound;
import br.com.adrianorodrigues.stocks.usecase.GetStockData;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.internal.verification.VerificationModeFactory;
import org.mockito.verification.VerificationMode;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

import java.math.BigDecimal;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestRedisConfiguration.class)
class StockControllerTest {

    @MockBean
    GetStockData getStockData;

    @LocalServerPort
    int port;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
    }

    @Test
    void getStockShouldReturnDataWhenSucceeded() {
        Mockito
                .when(getStockData.execute("B3SA3"))
                .thenReturn(Stock.builder().ticker("B3SA3").price(BigDecimal.TEN).build());

        given()
                .get("/stocks/B3SA3")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("ticker", equalTo("B3SA3"))
                .body("price", equalTo(10));
    }

    @Test
    void getStockShouldReturnDataWhenCalledTwiceShouldHitCache() {
        Mockito
                .when(getStockData.execute("BCFF11"))
                .thenReturn(Stock.builder().ticker("BCFF11").price(BigDecimal.TEN).build());

        given()
                .get("/stocks/BCFF11")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("ticker", equalTo("BCFF11"))
                .body("price", equalTo(10));
        given()
                .get("/stocks/BCFF11")
                .then()
                .log()
                .all()
                .statusCode(200)
                .body("ticker", equalTo("BCFF11"))
                .body("price", equalTo(10));

        Mockito
                .verify(getStockData, VerificationModeFactory.atMostOnce())
                .execute("BCFF11");
    }

    @Test
    void getStockShoultReturn404WhenDataNotFound() {
        Mockito
                .when(getStockData.execute("B3SA4"))
                .thenThrow(new StockDataNotFound("B3SA4"));

        given()
                .get("/stocks/B3SA4")
                .then()
                .log()
                .all()
                .statusCode(404);
    }
}
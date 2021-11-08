package br.com.adrianorodrigues.stocks.usecase;

import br.com.adrianorodrigues.stocks.domain.Stock;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetStockDataTest {

    @Autowired
    GetStockData getStockData;

    @Test
    void getExecuteShouldReturnPrice() throws IOException {
        Stock stock = getStockData.execute("B3SA3");
        assertTrue(stock.getPrice().compareTo(BigDecimal.ZERO) > 0);
    }

    @Test
    void getExecuteShouldReturnTicker() throws IOException {
        Stock stock = getStockData.execute("B3SA3");
        assertEquals("B3SA3", stock.getTicker());
    }
}
package br.com.adrianorodrigues.stocks.usecase;

import br.com.adrianorodrigues.stocks.domain.Stock;
import br.com.adrianorodrigues.stocks.exceptions.StockDataNotFound;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class GetStockData {

    public Stock execute(String ticker) {
        ticker = ticker.endsWith("F") ? ticker.substring(0, ticker.length() - 1) : ticker;
        try {
            Document doc = Jsoup.connect("https://finance.yahoo.com/quote/" + ticker + ".SA/?p=" + ticker + ".SA").get();
            Elements quoteHeader = doc.select("#quote-header-info");
            List<String> strings = new ArrayList<>();
            quoteHeader.first().getAllElements().forEach(element -> strings.add(element.text()));
            log.info(strings.toString());
            double price = Double.parseDouble(strings.get(27));
            return Stock
                    .builder()
                    .ticker(ticker)
                    .price(BigDecimal.valueOf(price))
                    .build();
        } catch (Exception e) {
            log.error("Unable to find stock price for: {}", ticker, e);
            throw new StockDataNotFound("Unable to find stock price for:" + ticker);
        }
    }

}
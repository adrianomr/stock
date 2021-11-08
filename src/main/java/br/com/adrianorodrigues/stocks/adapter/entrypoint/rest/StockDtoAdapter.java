package br.com.adrianorodrigues.stocks.adapter.entrypoint.rest;

import br.com.adrianorodrigues.stocks.domain.Stock;
import br.com.adrianorodrigues.stocks.entrypoint.rest.dto.StockDto;
import lombok.experimental.UtilityClass;

@UtilityClass
public class StockDtoAdapter {

    public static StockDto convert(Stock stock){
        return StockDto
                .builder()
                .price(stock.getPrice())
                .ticker(stock.getTicker())
                .build();
    }

}

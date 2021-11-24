package br.com.adrianorodrigues.stocks.entrypoint.rest;

import br.com.adrianorodrigues.stocks.adapter.entrypoint.rest.StockDtoAdapter;
import br.com.adrianorodrigues.stocks.entrypoint.rest.dto.StockDto;
import br.com.adrianorodrigues.stocks.usecase.GetStockData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("stocks")
public class StockController {

    @Autowired
    GetStockData getStockData;

    @GetMapping("/{ticker}")
    @Cacheable(value = "stock")
    public StockDto getStock(@PathVariable String ticker){
        return StockDtoAdapter
                .convert(getStockData.execute(ticker));
    }
}

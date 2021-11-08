package br.com.adrianorodrigues.stocks.entrypoint.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Getter
public class StockDto {

    private String ticker;
    private BigDecimal price;

}

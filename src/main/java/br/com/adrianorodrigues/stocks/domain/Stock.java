package br.com.adrianorodrigues.stocks.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Builder
@AllArgsConstructor
@Getter
public class Stock {

    private String ticker;
    private BigDecimal price;

}

package br.com.adrianorodrigues.stocks.exceptions;

import org.springframework.http.HttpStatus;

public class StockDataNotFound extends BaseException{
    public StockDataNotFound(String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}

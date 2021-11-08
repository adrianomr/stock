package br.com.adrianorodrigues.stocks.interceptor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Builder
@Getter
public class ErrorDto {

    private int status;
    private String message;

}

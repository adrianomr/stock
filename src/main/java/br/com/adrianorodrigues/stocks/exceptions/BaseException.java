package br.com.adrianorodrigues.stocks.exceptions;

import br.com.adrianorodrigues.stocks.interceptor.ErrorDto;
import org.springframework.http.HttpStatus;

public abstract class BaseException extends RuntimeException{

    private final HttpStatus statusCode;

    protected BaseException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    protected BaseException(String message, Throwable cause, HttpStatus statusCode) {
        super(message, cause);
        this.statusCode = statusCode;
    }

    public ErrorDto toErrorDto(){
        return ErrorDto
                .builder()
                .status(statusCode.value())
                .message(getMessage())
                .build();
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }
}

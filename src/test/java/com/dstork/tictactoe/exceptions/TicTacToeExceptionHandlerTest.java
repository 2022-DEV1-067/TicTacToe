package com.dstork.tictactoe.exceptions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class TicTacToeExceptionHandlerTest {

    @InjectMocks
    TicTacToeExceptionHandler ticTacToeExceptionHandler;



    @Test
    void resourceNotFoundExceptionHandler() {

        ResourceNotFoundException resourceNotFoundException = new ResourceNotFoundException();

        WebRequest request =  WebRequest.;



    }

    @Test
    void handleValidationExceptions() {
    }

    @Test
    void notAllowedExceptionHandler() {
    }

    @Test
    void badRequestExceptionHandler() {
    }
}
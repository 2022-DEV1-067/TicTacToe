package com.dstork.tictactoe.exceptions;

import com.dstork.tictactoe.model.Game;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
class TicTacToeExceptionHandlerTest {

    @InjectMocks
    TicTacToeExceptionHandler ticTacToeExceptionHandler;

    @Test
    void resourceNotFoundExceptionHandler() {
        WebRequest request = mock(WebRequest.class);
        ResponseEntity<ErrorResponse> wrongTurnError = ticTacToeExceptionHandler.resourceNotFoundExceptionHandler(
                new ResourceNotFoundException("It is not the player S'Turn"), request);
        System.out.println(wrongTurnError);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, wrongTurnError.getStatusCode());

    }

    @Test
    void handleValidationExceptions() {
        WebRequest request = mock(WebRequest.class);
        MethodParameter parameter = null;
        BindingResult bindingResult = new BeanPropertyBindingResult(parameter, "Error sample");
        ResponseEntity<ErrorResponse> wrongTurnError = ticTacToeExceptionHandler.handleValidationExceptions(
                new MethodArgumentNotValidException(parameter, bindingResult), request);
        System.out.println(wrongTurnError);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, wrongTurnError.getStatusCode());
    }

    @Test
    void notAllowedExceptionHandler() {
        WebRequest request = mock(WebRequest.class);
        ResponseEntity<ErrorResponse> wrongTurnError = ticTacToeExceptionHandler.notAllowedExceptionHandler(
                new NotAllowedException("Game already ended"), request);
        System.out.println(wrongTurnError);

        Assertions.assertEquals(HttpStatus.METHOD_NOT_ALLOWED, wrongTurnError.getStatusCode());
    }

    @Test
    void badRequestExceptionHandler() {
        WebRequest request = mock(WebRequest.class);
        ResponseEntity<ErrorResponse> wrongTurnError = ticTacToeExceptionHandler.badRequestExceptionHandler(
                new BadRequestException("The position is already filed In"), request);
        System.out.println(wrongTurnError);

        Assertions.assertEquals(HttpStatus.BAD_REQUEST, wrongTurnError.getStatusCode());
    }


}
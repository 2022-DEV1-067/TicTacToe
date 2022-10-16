package com.dstork.tictactoe.controller;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.fixture.GameFixture;
import com.dstork.tictactoe.model.Game;
import com.dstork.tictactoe.services.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @InjectMocks
    GameController gameController;

    @Mock
    GameService gameService;

    @LocalServerPort
    private int port;


    @Test
    void getGameByIdSucces() {

        GameDTO gameDTO = GameFixture.getSampleGameDTO(2);
        when(gameService.findGameById(any(Long.class))).thenReturn(Optional.of(gameDTO));

        Long gameId= 2L;

        ResponseEntity<GameDTO> responseEntity = gameController.getGameById(gameId);

        assertNotNull(gameDTO);
        Assertions.assertEquals(HttpStatus.OK,responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON,responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getPlayerX(),gameDTO.getPlayerX());

    }

    @Test
    void getGameByIdErrorGameNotFound() {

        when(gameService.findGameById(any(Long.class))).thenReturn(Optional.empty());

        Long gameId= 12L;

        ResponseEntity<GameDTO> responseEntity = gameController.getGameById(gameId);
        Assertions.assertEquals(HttpStatus.NOT_FOUND,responseEntity.getStatusCode());
    }






    @Test
    void startGame() {
    }

    @Test
    void cancelGame() {
    }

    @Test
    void play() {
    }
}
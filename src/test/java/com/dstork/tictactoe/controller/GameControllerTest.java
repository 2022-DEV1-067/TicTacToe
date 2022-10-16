package com.dstork.tictactoe.controller;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.exceptions.NotAllowedException;
import com.dstork.tictactoe.exceptions.ResourceNotFoundException;
import com.dstork.tictactoe.fixture.GameFixture;
import com.dstork.tictactoe.services.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.Objects;
import java.util.Optional;

import static com.dstork.tictactoe.enums.GameStatus.*;
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


    @Test
    void getGameByIdSuccess() {

        GameDTO gameDTO = GameFixture.getSampleGameDTO(2);
        when(gameService.findGameById(any(Long.class))).thenReturn(Optional.of(gameDTO));

        Long gameId = 2L;

        ResponseEntity<GameDTO> responseEntity = gameController.getGameById(gameId);

        assertNotNull(gameDTO);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getPlayerX(), gameDTO.getPlayerX());

    }

    @Test
    void getGameByIdErrorGameNotFound() {

        when(gameService.findGameById(any(Long.class))).thenReturn(Optional.empty());

        Long gameId = 12L;

        ResponseEntity<GameDTO> responseEntity = gameController.getGameById(gameId);
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    void startGameGameCreatedByFirstPlayer() {
        GameDTO gameDTO = GameFixture.getSampleGameDTO(1);
        when(gameService.startGame(any(String.class))).thenReturn(Optional.of(gameDTO));

        String PlayerLogin = "Zack";
        ResponseEntity<GameDTO> responseEntity = gameController.startGame(PlayerLogin);

        assertNotNull(gameDTO);
        Assertions.assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getPlayerX(), gameDTO.getPlayerX());
        Assertions.assertEquals(PENDING, Objects.requireNonNull(responseEntity.getBody()).getGameStatus());
        assertNull(responseEntity.getBody().getPlayerO());

    }

    @Test
    void startGameSecondPlayerJoinsTheGame() {

        GameDTO gameDTO = GameFixture.getSampleGameDTO(2);
        when(gameService.startGame(any(String.class))).thenReturn(Optional.of(gameDTO));

        String PlayerLogin = "Sns";

        ResponseEntity<GameDTO> responseEntity = gameController.startGame(PlayerLogin);

        assertNotNull(gameDTO);
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getPlayerX(), gameDTO.getPlayerX());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getPlayerO(), gameDTO.getPlayerO());
        Assertions.assertEquals(X_TURN, Objects.requireNonNull(responseEntity.getBody()).getGameStatus());

    }

    @Test
    void startGameErrorPlayerAlreadyInGame() {
        when(gameService.startGame(any(String.class))).thenThrow(NotAllowedException.class);
        String PlayerLogin = "Zack";
        Assertions.assertThrows(NotAllowedException.class, () -> gameController.startGame(PlayerLogin));

    }


    @Test
    void startGameErrorPlayerNotFound() {
        when(gameService.startGame(any(String.class))).thenThrow(ResourceNotFoundException.class);
        String PlayerLogin = "Alpha";
        Assertions.assertThrows(ResourceNotFoundException.class, () -> gameController.startGame(PlayerLogin));
    }


    @Test
    void cancelGame() {
    }

    @Test
    void play() {
    }
}
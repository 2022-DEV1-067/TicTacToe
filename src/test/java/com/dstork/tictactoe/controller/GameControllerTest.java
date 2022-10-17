package com.dstork.tictactoe.controller;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.dto.GameMoveDTO;
import com.dstork.tictactoe.enums.GameStatus;
import com.dstork.tictactoe.fixture.GameFixture;
import com.dstork.tictactoe.services.GameService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.dstork.tictactoe.enums.GameStatus.*;
import static com.dstork.tictactoe.enums.PositionValue.*;
import static com.dstork.tictactoe.fixture.GameFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @InjectMocks
    GameController gameController;

    @Mock
    GameService gameService;


    @Test
    void getGameByIdSuccess() {

        GameDTO gameDTO = GameFixture.getSampleGameDTO(2);
        when(gameService.findGameById(any(Long.class))).thenReturn(Optional.of(gameDTO));

        Long gameId = 2L;

        ResponseEntity<GameDTO> responseEntity = gameController.getGameById(gameId);

        assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getPlayerX(), gameDTO.getPlayerX());

    }

    @Test
    void getGameByIdErrorGameNotFound() {

        when(gameService.findGameById(any(Long.class))).thenReturn(Optional.empty());

        Long gameId = 12L;

        ResponseEntity<GameDTO> responseEntity = gameController.getGameById(gameId);
        assertNull(responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    void startGameGameCreatedByFirstPlayer() {
        GameDTO gameDTO = GameFixture.getSampleGameDTO(1);
        when(gameService.startGame(any(String.class))).thenReturn(Optional.of(gameDTO));

        String PlayerLogin = "Zack";
        ResponseEntity<GameDTO> responseEntity = gameController.startGame(PlayerLogin);

        assertNotNull(responseEntity.getBody());
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

        assertNotNull(responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getPlayerX(), gameDTO.getPlayerX());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getPlayerO(), gameDTO.getPlayerO());
        Assertions.assertEquals(X_TURN, Objects.requireNonNull(responseEntity.getBody()).getGameStatus());

    }


    @Test
    void cancelGameSuccess() {
        GameDTO gameDTO = GameFixture.getSampleGameDTO(2);
        gameDTO.setGameStatus(CANCELLED);
        when(gameService.cancelGame(any(Long.class))).thenReturn(Optional.of(gameDTO));

        ResponseEntity<GameDTO> responseEntity = gameController.cancelGame(2L);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getPlayerX(), gameDTO.getPlayerX());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getPlayerO(), gameDTO.getPlayerO());
        Assertions.assertEquals(CANCELLED, Objects.requireNonNull(responseEntity.getBody()).getGameStatus());
    }

    @Test
    void cancelGameErrorGameNotFound() {
        GameDTO gameDTO = GameFixture.getSampleGameDTO(2);
        gameDTO.setGameStatus(CANCELLED);
        when(gameService.cancelGame(any(Long.class))).thenReturn(Optional.empty());

        ResponseEntity<GameDTO> responseEntity = gameController.cancelGame(2L);
        assertNull(responseEntity.getBody());
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void cancelGameErrorGameCantBeCancelled() {
        GameDTO gameDTO = GameFixture.getSampleGameDTO(2);
        gameDTO.setGameStatus(O_WINS);
        when(gameService.cancelGame(any(Long.class))).thenReturn(Optional.empty());

        ResponseEntity<GameDTO> responseEntity = gameController.cancelGame(2L);

        List<GameStatus> StatusList = Arrays.asList(PENDING, O_TURN, X_TURN);
        assert (StatusList.stream().noneMatch(element -> gameDTO.getGameStatus().equals(element)));
        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }


    @Test
    void playSuccess() {

        GameDTO gameDTO = GameFixture.getSampleGameDTO(2);
        when(gameService.makeGameMove(any(GameMoveDTO.class))).thenReturn(Optional.of(gameDTO));
        gameDTO.setGameBoard(getGameBoardEmpty());
        GameMoveDTO gameMoveDTO = new GameMoveDTO("Zack", 1L, 5);

        gameDTO.getGameBoard().put(gameMoveDTO.getPosition(), X);
        gameDTO.setGameStatus(O_TURN);


        ResponseEntity<GameDTO> responseEntity = gameController.play(gameMoveDTO);

        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        Assertions.assertEquals(MediaType.APPLICATION_JSON, responseEntity.getHeaders().getContentType());
        assertNotNull(responseEntity.getBody());
        List<GameStatus> StatusList = Arrays.asList(O_TURN, X_TURN);
        assert (StatusList.stream().anyMatch(element -> responseEntity.getBody().getGameStatus().equals(element)));
        assertEquals(X, responseEntity.getBody().getGameBoard().getOrDefault(5, null));
        assertEquals(O_TURN, responseEntity.getBody().getGameStatus());
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertTrue(responseEntity.getBody().getGameBoard().size() < 10);
        assertTrue(gameMoveDTO.getPosition() > 0);
        assertTrue(gameMoveDTO.getPosition() < 10);
    }

    @Test
    void playErrorPositionFiled() {
        GameDTO gameDTO = GameFixture.getSampleGameDTO(2);
        when(gameService.makeGameMove(any(GameMoveDTO.class))).thenReturn(Optional.empty());
        GameMoveDTO gameMoveDTO = new GameMoveDTO("Zack", 1L, 8);
        GameMoveDTO gameMoveDTO2 = new GameMoveDTO("Sns", 1L, 8);

        gameDTO.setGameBoard(getGameBoardEmpty());
        gameDTO.getGameBoard().put(gameMoveDTO.getPosition(), X);
        gameDTO.getGameBoard().put(gameMoveDTO2.getPosition(), O);

        ResponseEntity<GameDTO> responseEntity = gameController.play(gameMoveDTO);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(gameDTO.getGameBoard().getOrDefault(8, null));

    }


    @Test
    void playErrorNotPlayerSTurn() {
        GameDTO gameDTO = GameFixture.getSampleGameDTO(2);
        GameMoveDTO gameMoveDTO = new GameMoveDTO("Zack", 1L, 4);
        gameDTO.setGameStatus(O_TURN);
        gameDTO.setGameBoard(getGameBoardEmpty());
        gameDTO.getGameBoard().put(gameMoveDTO.getPosition(), X);
        when(gameService.makeGameMove(any(GameMoveDTO.class))).thenReturn(Optional.empty());

        ResponseEntity<GameDTO> responseEntity = gameController.play(gameMoveDTO);


        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNotNull(gameDTO.getGameBoard().getOrDefault(4, null));
        assertNotEquals(X_TURN, Objects.requireNonNull(gameDTO.getGameStatus()));
    }


    @Test
    void playErrorGameNotFound() {
        GameMoveDTO gameMoveDTO = new GameMoveDTO("Zack", 1L, 4);
        when(gameService.makeGameMove(any(GameMoveDTO.class))).thenReturn(Optional.empty());

        ResponseEntity<GameDTO> responseEntity = gameController.play(gameMoveDTO);

        Assertions.assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void playErrorGameEnded() {
        GameDTO gameDTO = GameFixture.getSampleGameDTO(2);
        GameMoveDTO gameMoveDTO = new GameMoveDTO("Zack", 1L, 4);
        gameDTO.setGameBoard(getGameBoardWhenDraw());
        gameDTO.setGameStatus(DRAW);
        when(gameService.makeGameMove(any(GameMoveDTO.class))).thenReturn(Optional.of(gameDTO));
        ResponseEntity<GameDTO> responseEntity = gameController.play(gameMoveDTO);

        List<GameStatus> StatusList = Arrays.asList(DRAW, O_WINS, X_WINS);
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getPlayerX(), gameDTO.getPlayerX());
        Assertions.assertEquals(Objects.requireNonNull(responseEntity.getBody()).getPlayerO(), gameDTO.getPlayerO());
        assert(StatusList.stream().anyMatch(element -> Objects.requireNonNull(responseEntity.getBody()).getGameStatus().equals(element)));
    }

}
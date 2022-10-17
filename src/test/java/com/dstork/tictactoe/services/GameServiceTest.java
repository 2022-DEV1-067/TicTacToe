package com.dstork.tictactoe.services;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.exceptions.NotAllowedException;
import com.dstork.tictactoe.exceptions.ResourceNotFoundException;
import com.dstork.tictactoe.fixture.GameFixture;
import com.dstork.tictactoe.mapper.GameMapper;
import com.dstork.tictactoe.model.Game;
import com.dstork.tictactoe.repository.GameRepository;
import com.dstork.tictactoe.repository.PlayerRepository;
import com.dstork.tictactoe.services.Imp.GameServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static com.dstork.tictactoe.enums.GameStatus.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.nullable;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    GameServiceImpl gameService;

    @Mock
    GameRepository gameRepository;

    @Mock
    PlayerRepository playerRepository;

    @Mock
    GameMapper gameMapper;

    @Test
    void findGameByIdSuccess() {
        Game game = GameFixture.getSampleGame(1);
        GameDTO gameDTO = GameFixture.getSampleGameDTO(1);
        when(gameRepository.findById(nullable(Long.class))).thenReturn(Optional.of(game));
        when(gameMapper.mapGameToGameDTO(any(Game.class))).thenReturn(gameDTO);

        Optional<GameDTO> optionalGameDTO = gameService.findGameById(1L);

        assertNotNull(gameRepository.findById(1L));
        Assertions.assertEquals(1L, (optionalGameDTO.get().getVersion()));
        Assertions.assertEquals(optionalGameDTO.get().getGameStatus(), gameDTO.getGameStatus());
        Assertions.assertEquals(optionalGameDTO.get().getPlayerX(), gameDTO.getPlayerX());
        Assertions.assertEquals(optionalGameDTO.get().getGameBoard(), gameDTO.getGameBoard());

    }


    @Test
    void findGameByIdErrorNotFound() {
        when(gameRepository.findById(nullable(Long.class))).thenReturn(Optional.empty());

        Assertions.assertThrows(ResourceNotFoundException.class, () -> gameService.findGameById(2L));
    }


    @Test
    void startGameOnePlayerGamePendingSuccess() {
        Game game = GameFixture.getSampleGame(1);
        GameDTO gameDTO = GameFixture.getSampleGameDTO(1);

        when(playerRepository.findByLogin(nullable(String.class))).thenReturn(Optional.of(game.getPlayerX()));
        when(gameRepository.findByGameStatusInAndPlayerX_IdOrGameStatusInAndPlayerO_Id(any(List.class),
                nullable(Long.class),
                any(List.class),
                nullable(Long.class)))
                .thenReturn(Collections.emptyList());
        when(gameRepository.findByGameStatus(PENDING)).thenReturn(Collections.emptyList());
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(gameMapper.mapGameToGameDTO(any(Game.class))).thenReturn(gameDTO);

        Optional<GameDTO> optionalGameDTO = gameService.startGame("Zack");

        assertNotNull(gameRepository.findById(1L));
        Assertions.assertEquals(1L, optionalGameDTO.get().getVersion());
        Assertions.assertEquals(optionalGameDTO.get().getGameStatus(), gameDTO.getGameStatus());
        Assertions.assertEquals(optionalGameDTO.get().getPlayerX(), gameDTO.getPlayerX());
        Assertions.assertEquals(optionalGameDTO.get().getGameBoard(), gameDTO.getGameBoard());
    }

    @Test
    void startGameTwoPlayersGameXTurnSuccess() {
        Game game = GameFixture.getSampleGame(2);
        GameDTO gameDTO = GameFixture.getSampleGameDTO(2);

        List<Game> gamesList = List.of(game);

        when(playerRepository.findByLogin(nullable(String.class))).thenReturn(Optional.of(game.getPlayerX()));
        when(gameRepository.findByGameStatusInAndPlayerX_IdOrGameStatusInAndPlayerO_Id(any(List.class),
                nullable(Long.class),
                any(List.class),
                nullable(Long.class)))
                .thenReturn(Collections.emptyList());
        when(gameRepository.findByGameStatus(PENDING)).thenReturn(gamesList);
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(gameMapper.mapGameToGameDTO(any(Game.class))).thenReturn(gameDTO);

        Optional<GameDTO> optionalGameDTO = gameService.startGame("Sns");

        assertNotNull(gameRepository.findById(2L));
        Assertions.assertEquals(2, optionalGameDTO.get().getVersion());
        Assertions.assertEquals(X_TURN,optionalGameDTO.get().getGameStatus() );
        Assertions.assertEquals(optionalGameDTO.get().getPlayerX(), gameDTO.getPlayerX());
        Assertions.assertEquals(optionalGameDTO.get().getPlayerO(), gameDTO.getPlayerO());
        Assertions.assertEquals(optionalGameDTO.get().getGameBoard(), gameDTO.getGameBoard());
    }

    @Test
    void startGameErrorPlayerNotFound() {
        when(playerRepository.findByLogin(nullable(String.class))).thenThrow(ResourceNotFoundException.class);
        assertThrows(ResourceNotFoundException.class, () -> gameService.startGame("Sns"));
    }


    @Test
    void startGameErrorPlayerAlreadyInGame() {
        Game game = GameFixture.getSampleGame(2);

        when(playerRepository.findByLogin(nullable(String.class))).thenReturn(Optional.of(game.getPlayerX()));
        when(gameRepository.findByGameStatusInAndPlayerX_IdOrGameStatusInAndPlayerO_Id(any(List.class),
                nullable(Long.class),
                any(List.class),
                nullable(Long.class)))
                .thenReturn(List.of(game));

        assertThrows(NotAllowedException.class, () -> gameService.startGame("Sns"));
    }

    @Test
    void cancelGameSuccess() {
        Game game = GameFixture.getSampleGame(1);
        GameDTO gameDTO = GameFixture.getSampleGameDTO(1);
        gameDTO.setGameStatus(CANCELLED);
        gameDTO.setVersion(5L);

        when(gameRepository.findById(nullable(Long.class))).thenReturn(Optional.of(game));
        when(gameRepository.save(any(Game.class))).thenReturn(game);
        when(gameMapper.mapGameToGameDTO(any(Game.class))).thenReturn(gameDTO);

        Optional<GameDTO> optionalGameDTO = gameService.cancelGame(1L);
        Assertions.assertNotNull(optionalGameDTO.get());
        Assertions.assertEquals(CANCELLED, optionalGameDTO.get().getGameStatus());
        Assertions.assertEquals(5L, optionalGameDTO.get().getVersion());
        Assertions.assertEquals(optionalGameDTO.get().getPlayerX(), gameDTO.getPlayerX());
        Assertions.assertEquals(optionalGameDTO.get().getGameBoard(), gameDTO.getGameBoard());

    }


    @Test
    void cancelGameErrorGameNotFound() {
        when(gameRepository.findById(nullable(Long.class))).thenReturn(Optional.empty());
        Assertions.assertThrows(ResourceNotFoundException.class, () -> gameService.cancelGame(2L));
    }

    @Test
    void cancelGameErrorGameCantBeCancelled() {
        Game game = GameFixture.getSampleGame(2);
        game.setGameStatus(DRAW);

        when(gameRepository.findById(nullable(Long.class))).thenReturn(Optional.of(game));

        Assertions.assertThrows(NotAllowedException.class, () -> gameService.cancelGame(2L));
    }


    @Test
    void makeGameMove() {
    }


}
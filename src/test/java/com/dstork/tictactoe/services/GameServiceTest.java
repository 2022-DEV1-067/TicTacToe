package com.dstork.tictactoe.services;

import com.dstork.tictactoe.mapper.GameMapper;
import com.dstork.tictactoe.repository.GameRepository;
import com.dstork.tictactoe.repository.PlayerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GameServiceTest {

    @InjectMocks
    GameService gameService;

    @Mock
    GameRepository gameRepository;

    @Mock
    PlayerRepository playerRepository;

    @Mock
    GameMapper gameMapper;

    @Test
    void findGameById() {
    }

    @Test
    void startGame() {
    }

    @Test
    void cancelGame() {
    }

    @Test
    void makeGameMove() {
    }
}
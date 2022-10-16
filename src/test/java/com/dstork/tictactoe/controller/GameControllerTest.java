package com.dstork.tictactoe.controller;

import com.dstork.tictactoe.services.GameService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class GameControllerTest {

    @InjectMocks
    GameController gameController;

    @Mock
    GameService gameService;

    @Test
    void getGameById() {
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
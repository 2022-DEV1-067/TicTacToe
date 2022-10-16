package com.dstork.tictactoe.services;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.dto.GameMoveDTO;

import java.util.Optional;

public interface GameService {
    Optional<GameDTO> findGameById(Long id);
    Optional<GameDTO> startGame(String playerLogin);
    Optional<GameDTO> cancelGame(Long gameId);
    Optional<GameDTO> makeGameMove(GameMoveDTO gameMoveDTO);
}

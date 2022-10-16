package com.dstork.tictactoe.services;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.dto.GameMoveDTO;

public interface GameService {
    GameDTO findGameById(Long id);
    GameDTO startGame(String playerLogin);
    GameDTO cancelGame(Long gameId);
    GameDTO makeGameMove(GameMoveDTO gameMoveDTO);
}

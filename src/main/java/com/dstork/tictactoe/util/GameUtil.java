package com.dstork.tictactoe.util;

import com.dstork.tictactoe.enums.PositionValue;
import com.dstork.tictactoe.model.Game;
import com.dstork.tictactoe.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;

import static com.dstork.tictactoe.enums.GameStatus.PENDING;
import static com.dstork.tictactoe.enums.GameStatus.X_TURN;

public class GameUtil {
    private static final Logger logger = LogManager.getLogger(GameUtil.class);


    public static Game startGame(Player player, Game game) {
        if (game == null) {
            game = new Game();
            game.setPlayerX(player);
            game.setGameStatus(PENDING);
            logger.info("Game Pending, Waiting for player O to join ");

        } else {
            game.setPlayerO(player);
            game.setGameStatus(X_TURN);
            game = fillGameBoard(game);
            logger.info("Player {} AND player {} Started Playing ", game.getPlayerX().getLogin(), game.getPlayerO().getLogin());
        }
        return game;
    }


    private static Game fillGameBoard(Game game) {

        Map<Integer, PositionValue> gameBoard = new HashMap<>();

        gameBoard.put(1, null);
        gameBoard.put(2, null);
        gameBoard.put(3, null);
        gameBoard.put(4, null);
        gameBoard.put(5, null);
        gameBoard.put(6, null);
        gameBoard.put(7, null);
        gameBoard.put(8, null);
        gameBoard.put(9, null);

        game.setGameBoard(gameBoard);
        return game;
    }


    private GameUtil() {
    }

}

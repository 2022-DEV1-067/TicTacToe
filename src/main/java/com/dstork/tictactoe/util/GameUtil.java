package com.dstork.tictactoe.util;

import com.dstork.tictactoe.enums.GameStatus;
import com.dstork.tictactoe.enums.PositionValue;
import com.dstork.tictactoe.model.Game;
import com.dstork.tictactoe.model.Player;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.dstork.tictactoe.enums.GameStatus.PENDING;
import static com.dstork.tictactoe.enums.GameStatus.X_TURN;

public class GameUtil {
    private static final Logger logger = LogManager.getLogger(GameUtil.class);

    public static final String LINE_SEPARATION = "-------------------------";
    public static final String GAME_BOARD_LINE = "|   {}   |   {}   |   {}   |";

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

    public static void printGameBoard(Map<Integer, PositionValue> gameBoard) {
        logger.info(LINE_SEPARATION);
        logger.info(GAME_BOARD_LINE, getValuePosition(gameBoard, 1), getValuePosition(gameBoard, 2), getValuePosition(gameBoard, 3));
        logger.info(LINE_SEPARATION);
        logger.info(GAME_BOARD_LINE, getValuePosition(gameBoard, 4), getValuePosition(gameBoard, 5), getValuePosition(gameBoard, 6));
        logger.info(LINE_SEPARATION);
        logger.info(GAME_BOARD_LINE, getValuePosition(gameBoard, 7), getValuePosition(gameBoard, 8), getValuePosition(gameBoard, 9));
        logger.info(LINE_SEPARATION);
    }

    public static GameStatus checkBoardState(Map<Integer, PositionValue> gameBoard) {
        for (int i = 1; i < 9; i++) {
            String line = "";
            switch (i) {
                case 1:
                    line = getValuePosition(gameBoard, 1) + getValuePosition(gameBoard, 2) + getValuePosition(gameBoard, 3);
                    break;
                case 2:
                    line = getValuePosition(gameBoard, 4) + getValuePosition(gameBoard, 5) + getValuePosition(gameBoard, 6);
                    break;
                case 3:
                    line = getValuePosition(gameBoard, 7) + getValuePosition(gameBoard, 8) + getValuePosition(gameBoard, 9);
                    break;
                case 4:
                    line = getValuePosition(gameBoard, 1) + getValuePosition(gameBoard, 4) + getValuePosition(gameBoard, 7);
                    break;
                case 5:
                    line = getValuePosition(gameBoard, 2) + getValuePosition(gameBoard, 5) + getValuePosition(gameBoard, 8);
                    break;
                case 6:
                    line = getValuePosition(gameBoard, 3) + getValuePosition(gameBoard, 6) + getValuePosition(gameBoard, 9);
                    break;
                case 7:
                    line = getValuePosition(gameBoard, 1) + getValuePosition(gameBoard, 5) + getValuePosition(gameBoard, 9);
                    break;
                case 8:
                    line = getValuePosition(gameBoard, 3) + getValuePosition(gameBoard, 5) + getValuePosition(gameBoard, 7);
                    break;
                default:
                    break;
            }
            if (line.equals("XXX")) {
                return GameStatus.X_WINS;
            } else if (line.equals("OOO")) {
                return GameStatus.O_WINS;
            }
        }
        if (gameBoard.values().stream().filter(Objects::nonNull).count() == 9) {
            return GameStatus.DRAW;
        }
        return null;
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


    public static String getValuePosition(Map<Integer, PositionValue> gameBoard, int index) {
        return printPositionValue(gameBoard.getOrDefault(index, null));
    }

    public static String printPositionValue(PositionValue positionValue) {
        return positionValue == null ? "-" : positionValue.toString();
    }

    private GameUtil() {
    }

}

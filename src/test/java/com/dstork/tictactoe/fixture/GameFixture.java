package com.dstork.tictactoe.fixture;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.dto.PlayerDTO;
import com.dstork.tictactoe.enums.PositionValue;
import com.dstork.tictactoe.model.Game;
import com.dstork.tictactoe.model.Player;
import lombok.Getter;
import java.util.HashMap;
import java.util.Map;
import static com.dstork.tictactoe.enums.GameStatus.*;
import static com.dstork.tictactoe.enums.PositionValue.*;

@Getter
public class GameFixture {


    public static Game getSampleGame(int PlayersNumber) {
        Game game = new Game();
        Player playerX = new Player("Zack");
        if (PlayersNumber == 1) {
            game.setId(1L);
            game.setVersion(1L);
            game.setPlayerX(playerX);
            game.setGameStatus(PENDING);
        } else {
            Player playerO = new Player("Sns");
            game.setId(2L);
            game.setVersion(2L);
            game.setPlayerX(playerX);
            game.setPlayerO(playerO);
            game.setGameStatus(O_TURN);

        }
        return game;
    }

    public static GameDTO getSampleGameDTO(int PlayersNumber) {
        GameDTO gameDTO = new GameDTO();

        PlayerDTO playerX = new PlayerDTO("Zack");
        if (PlayersNumber == 1) {
            gameDTO.setId(1L);
            gameDTO.setVersion(1L);
            gameDTO.setPlayerX(playerX);
            gameDTO.setGameStatus(PENDING);
        } else if (PlayersNumber == 2)  {
            PlayerDTO playerO = new PlayerDTO("Sns");
            gameDTO.setId(2L);
            gameDTO.setVersion(2L);
            gameDTO.setPlayerX(playerX);
            gameDTO.setPlayerO(playerO);
            gameDTO.setGameStatus(X_TURN);

        }
        return gameDTO;
    }



    public static Map<Integer, PositionValue> getGameBoardEmpty(){

        Map<Integer, PositionValue> GameBoard = new HashMap<>();
        GameBoard.put(1, null);
        GameBoard.put(2, null);
        GameBoard.put(3, null);
        GameBoard.put(4, null);
        GameBoard.put(5, null);
        GameBoard.put(6, null);
        GameBoard.put(7, null);
        GameBoard.put(8, null);
        GameBoard.put(9, null);
        return GameBoard;
    }


    public static Map<Integer, PositionValue> getGameBoardWhenDraw (){

        Map<Integer, PositionValue> GameBoard = new HashMap<>();
        GameBoard.put(1, X);
        GameBoard.put(2, X);
        GameBoard.put(3, O);
        GameBoard.put(4, O);
        GameBoard.put(5, O);
        GameBoard.put(6, X);
        GameBoard.put(7, X);
        GameBoard.put(8, O);
        GameBoard.put(9, O);
        return GameBoard;
    }

}

package com.dstork.tictactoe.mapper;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.enums.GameStatus;
import com.dstork.tictactoe.model.Game;
import com.dstork.tictactoe.model.Player;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.junit.jupiter.api.Assertions.*;


class GameMapperTest {

    private final GameMapper gameMapper = new GameMapper(new ModelMapper());

    @Test
    void MapGameToGameDTO(){

        //Creation of assets
        Game game = getSampleGame();

        //Applying the mothod
        GameDTO gameDTO = gameMapper.mapGameToGameDTO(game);

        //cheking the Result
        assertNotNull(gameDTO);
        assertEquals(gameDTO.getGameStatus(),game.getGameStatus());
        assertEquals(gameDTO.getPlayerO().getLogin(),game.getPlayerO().getLogin());
        assertEquals(gameDTO.getPlayerX().getLogin(),game.getPlayerX().getLogin());


    }


    private Game getSampleGame(){
        Player playerX = new Player("Zack");
        Player playerO = new Player("Sns");

        Game game = new Game();
        game.setPlayerO(playerO);
        game.setPlayerX(playerX);
        game.setGameStatus(GameStatus.O_TURN);
        return game;
    }

}
package com.dstork.tictactoe.mapper;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.enums.GameStatus;
import com.dstork.tictactoe.fixture.GameFixture;
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
        Game game = GameFixture.getSampleGame(2);

        //Applying the mothod
        GameDTO gameDTO = gameMapper.mapGameToGameDTO(game);

        //cheking the Result
        assertNotNull(gameDTO);
        assertEquals(gameDTO.getGameStatus(),game.getGameStatus());
        assertEquals(gameDTO.getPlayerO().getLogin(),game.getPlayerO().getLogin());
        assertEquals(gameDTO.getPlayerX().getLogin(),game.getPlayerX().getLogin());

    }




}
package com.dstork.tictactoe.mapper;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.model.Game;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GameMapper {
    final
    ModelMapper modelMapper;

    @Autowired
    public GameMapper(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public GameDTO mapGameToGameDTO(Game game) {
        return modelMapper.map(game, GameDTO.class);
    }
}


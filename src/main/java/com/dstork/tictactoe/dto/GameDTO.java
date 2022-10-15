package com.dstork.tictactoe.dto;

import com.dstork.tictactoe.enums.GameStatus;
import com.dstork.tictactoe.enums.PositionValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameDTO extends AbstractDTO{
    private PlayerDTO playerX;
    private PlayerDTO playerO;
    private GameStatus gameStatus;
    private Map<Integer, PositionValue> gameBoard;
}

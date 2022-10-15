package com.dstork.tictactoe.model;

import com.dstork.tictactoe.enums.GameStatus;
import com.dstork.tictactoe.enums.PositionValue;
import lombok.*;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
public class Game {
    private Long id;
    private Player playerX;
    private Player playerO;
    private GameStatus gameStatus;
    private Map<Integer, PositionValue> gameBoard ;
}

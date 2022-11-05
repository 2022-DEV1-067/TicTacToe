package com.dstork.tictactoe.model;

import com.dstork.tictactoe.enums.GameStatus;
import com.dstork.tictactoe.enums.PositionValue;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@ToString
@Getter
@Setter
@Entity
public class Game extends AbstractEntity {
    @OneToOne
    private Player playerX;

    @OneToOne
    private Player playerO;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private GameStatus gameStatus;

    @ElementCollection
    @MapKeyColumn(name = "position")
    @Enumerated(EnumType.STRING)
    @Column(name = "position_value", nullable = false)
    @CollectionTable(name = "gameBoards_values", joinColumns = @JoinColumn(name = "game_id"))
    private Map<Integer, PositionValue> gameBoard;


    public Game(){
        this.gameBoard = new HashMap<>();
        gameBoard.put(1, null);
        gameBoard.put(2, null);
        gameBoard.put(3, null);
        gameBoard.put(4, null);
        gameBoard.put(5, null);
        gameBoard.put(6, null);
        gameBoard.put(7, null);
        gameBoard.put(8, null);
        gameBoard.put(9, null);
    }

}

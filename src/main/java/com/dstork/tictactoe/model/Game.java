package com.dstork.tictactoe.model;

import com.dstork.tictactoe.enums.GameStatus;
import com.dstork.tictactoe.enums.PositionValue;
import lombok.*;

import javax.persistence.*;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
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
}

package com.dstork.tictactoe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameMoveDTO extends  AbstractDTO{
    @NotNull
    private String playerLogin;
    @NotNull
    private Long gameId;
    @Range(from = 1, to= 9)
    private int position;
}

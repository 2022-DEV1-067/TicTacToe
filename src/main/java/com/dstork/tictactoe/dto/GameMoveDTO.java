package com.dstork.tictactoe.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameMoveDTO extends  AbstractDTO{
    @NotNull
    private String playerLogin;
    @NotNull
    private Long gameId;
    @Max(9)
    @Min(1)
    private int position;
}

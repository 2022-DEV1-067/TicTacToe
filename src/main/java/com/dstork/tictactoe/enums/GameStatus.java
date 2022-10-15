package com.dstork.tictactoe.enums;

public enum GameStatus {
    PENDING("Game Pending"),
    X_TURN("X Player Turn"),
    O_TURN("Y Player Turn"),
    X_WINS("X Player Wins"),
    O_WINS("Y Player Wins"),
    DRAW("Draw"),
    CANCELLED("Game Cancelled");
    private final String value;

    GameStatus(String value) {
        this.value = value;
    }
}

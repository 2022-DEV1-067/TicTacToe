package com.dstork.tictactoe.controller;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.dto.GameMoveDTO;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("/game")
public class GameController {

    private final String GAME = "/TicTacToe/Game/";

    @GetMapping(path = "/{Id}", produces = "application/json")
    public ResponseEntity<GameDTO> getGameById(@PathVariable Long Id) {
        return null;
    }
    @PostMapping(path = "/startGame", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GameDTO> startGame(@RequestBody @Valid String playerLogin) {
        return null;
    }

    @PostMapping(path = "/cancel", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GameDTO> cancelGame(@RequestBody @Valid Long Id){
        return null;
    }

    @PutMapping(path = "/makeMove", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GameDTO> play(@Valid @RequestBody GameMoveDTO gameMoveDTO) {
        return null;
    }

}

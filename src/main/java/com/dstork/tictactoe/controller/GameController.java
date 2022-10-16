package com.dstork.tictactoe.controller;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.dto.GameMoveDTO;
import com.dstork.tictactoe.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    private final GameService gameService;
    private static final String GAME = "/TicTacToe/Game/";


    @Operation(description = "Retrieving a game")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Game Retrieved successfully"),
            @ApiResponse(responseCode = "404", description = "The game is not found")})
    @GetMapping(path = "/{Id}", produces = "application/json")
    public ResponseEntity<GameDTO> getGameById(@PathVariable Long Id) {
        return gameService.findGameById(Id).map(
                gameDTO -> ResponseEntity
                        .ok()
                        .header(GAME + Id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .eTag(Long.toString(gameDTO.getVersion()))
                        .body(gameDTO)).orElse(ResponseEntity.notFound().build());
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

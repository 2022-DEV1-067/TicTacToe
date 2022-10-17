package com.dstork.tictactoe.controller;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.dto.GameMoveDTO;
import com.dstork.tictactoe.services.GameService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
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
    @GetMapping(path = "/{gameId}", produces = "application/json")
    public ResponseEntity<GameDTO> getGameById(@PathVariable Long gameId) {
        return gameService.findGameById(gameId).map(
                gameDTO -> ResponseEntity
                        .ok()
                        .header(GAME + gameId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .eTag(Long.toString(gameDTO.getVersion()))
                        .body(gameDTO)).orElse(ResponseEntity.notFound().build());
    }

    @Operation(description = "This methods acts as if there is a pending game the player will be joining it ," +
            " else he will create a game and wait for the second player to join with this exact method")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "First player create a game"),
            @ApiResponse(responseCode = "200", description = "Second player joins the pending game"),
            @ApiResponse(responseCode = "404", description = "The player was not found"),
            @ApiResponse(responseCode = "405", description = "The Player is already in game")})
    @PostMapping(path = "/startGame", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GameDTO> startGame(@RequestBody @Valid String playerLogin) {

        return gameService.startGame(playerLogin).map(gameDTO ->
        {
            if (gameDTO.getPlayerO() != null) {
                return ResponseEntity
                        .ok()
                        .header(GAME + gameDTO.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .eTag(Long.toString(gameDTO.getVersion()))
                        .body(gameDTO);
            } else {
                return ResponseEntity
                        .created(URI.create(GAME + gameDTO.getId().toString()))
                        .contentType(MediaType.APPLICATION_JSON)
                        .eTag(Long.toString(gameDTO.getVersion()))
                        .body(gameDTO);
            }
        }).orElse(ResponseEntity.notFound().build());
    }

    @Operation(description = "Cancels a game ")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "The game is successfully canceled"),
            @ApiResponse(responseCode = "404", description = "The game was not found"),
            @ApiResponse(responseCode = "405", description = "The game can't be cancelled")})
    @PostMapping(path = "/cancel", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GameDTO> cancelGame(@RequestBody @Valid Long id) {
        return gameService.cancelGame(id).map(
                gameDTO -> ResponseEntity
                        .ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .eTag(Long.toString(gameDTO.getVersion()))
                        .body(gameDTO)).orElse(ResponseEntity.notFound().build());
    }

    @Operation(description = "The Player makes his move on one of the available game board positions")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "The Player played successfully"),
            @ApiResponse(responseCode = "400", description = "The game board position is already filled, It is Not the player's turn," +
                    "Fields validation errors"),
            @ApiResponse(responseCode = "404", description = "Player not found or game not found"),
            @ApiResponse(responseCode = "405", description = "Game ended, or The player is not in the game")})
    @PutMapping(path = "/makeMove", produces = "application/json", consumes = "application/json")
    public ResponseEntity<GameDTO> play(@Valid @RequestBody GameMoveDTO gameMoveDTO) {
        return gameService.makeGameMove(gameMoveDTO).map(gameDTO ->
                ResponseEntity
                        .ok()
                        .header(GAME + gameDTO.getId().toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .eTag(Long.toString(gameDTO.getVersion()))
                        .body(gameDTO)).orElse(ResponseEntity.notFound().build());
    }

}

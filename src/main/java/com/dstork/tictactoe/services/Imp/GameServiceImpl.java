package com.dstork.tictactoe.services.Imp;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.dto.GameMoveDTO;
import com.dstork.tictactoe.exceptions.ResourceNotFoundException;
import com.dstork.tictactoe.mapper.GameMapper;
import com.dstork.tictactoe.model.Game;
import com.dstork.tictactoe.repository.GameRepository;
import com.dstork.tictactoe.repository.PlayerRepository;
import com.dstork.tictactoe.services.GameService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    Logger logger = LogManager.getLogger(GameServiceImpl.class);

    final GameMapper gameMapper;

    @Override
    public Optional<GameDTO> findGameById(Long id) {
        Game game = gameRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Game with Id: " + id + " Not Found"));

        logger.info("Game Details with Id : {}  Retrieved.", game.getId());
        return Optional.of(gameMapper.mapGameToGameDTO(game));
    }

    @Override
    public Optional<GameDTO> startGame(String playerLogin) {
        return null;
    }

    @Override
    public Optional<GameDTO> cancelGame(Long gameId) {
        return null;
    }

    @Override
    public Optional<GameDTO> makeGameMove(GameMoveDTO gameMoveDTO) {
        return null;
    }
}

package com.dstork.tictactoe.services.Imp;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.dto.GameMoveDTO;
import com.dstork.tictactoe.repository.GameRepository;
import com.dstork.tictactoe.repository.PlayerRepository;
import com.dstork.tictactoe.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    @Override
    public Optional<GameDTO> findGameById(Long id) {
        return null;
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

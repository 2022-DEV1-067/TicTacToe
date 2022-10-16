package com.dstork.tictactoe.services.Imp;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.dto.GameMoveDTO;
import com.dstork.tictactoe.repository.GameRepository;
import com.dstork.tictactoe.repository.PlayerRepository;
import com.dstork.tictactoe.services.GameService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    @Override
    public GameDTO findGameById(Long id) {
        return null;
    }

    @Override
    public GameDTO startGame(String playerLogin) {
        return null;
    }

    @Override
    public GameDTO cancelGame(Long gameId) {
        return null;
    }

    @Override
    public GameDTO makeGameMove(GameMoveDTO gameMoveDTO) {
        return null;
    }
}

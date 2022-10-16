package com.dstork.tictactoe.services.Imp;

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

}

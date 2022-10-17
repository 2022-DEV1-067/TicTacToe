package com.dstork.tictactoe.services.Imp;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.dto.GameMoveDTO;
import com.dstork.tictactoe.enums.GameStatus;
import com.dstork.tictactoe.exceptions.NotAllowedException;
import com.dstork.tictactoe.exceptions.ResourceNotFoundException;
import com.dstork.tictactoe.mapper.GameMapper;
import com.dstork.tictactoe.model.Game;
import com.dstork.tictactoe.model.Player;
import com.dstork.tictactoe.repository.GameRepository;
import com.dstork.tictactoe.repository.PlayerRepository;
import com.dstork.tictactoe.services.GameService;
import com.dstork.tictactoe.util.GameUtil;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.dstork.tictactoe.enums.GameStatus.*;

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
        List<GameStatus> statusList = Arrays.asList(PENDING, O_TURN, X_TURN);

        Player player = playerRepository.findByLogin(playerLogin)
                .orElseThrow(() -> new ResourceNotFoundException("Player with Login " + playerLogin + " Not Found"));

        List<Game> playerGames = gameRepository.findByGameStatusInAndPlayerX_IdOrGameStatusInAndPlayerO_Id(statusList, player.getId(), statusList, player.getId());
        if (!isEmptyList(playerGames)) {
            throw new NotAllowedException("Player with Login " + playerLogin + " Already in game");
        }
        Game pendingGame = findGameByStatusPENDING();
        Game game = GameUtil.startGame(player, pendingGame);

        game = gameRepository.save(game);
        return Optional.of(gameMapper.mapGameToGameDTO(game));
    }

    @Override
    public Optional<GameDTO> cancelGame(Long gameId) {
        return null;
    }

    @Override
    public Optional<GameDTO> makeGameMove(GameMoveDTO gameMoveDTO) {
        return null;
    }

    private Game findGameByStatusPENDING() {
        return gameRepository.findByGameStatus(PENDING).stream().findAny().orElse(null);
    }
    private static boolean isEmptyList(List<Game> playerGames) {
        return playerGames == null || playerGames.isEmpty();
    }

}

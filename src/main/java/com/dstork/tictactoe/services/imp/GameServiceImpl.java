package com.dstork.tictactoe.services.imp;

import com.dstork.tictactoe.dto.GameDTO;
import com.dstork.tictactoe.dto.GameMoveDTO;
import com.dstork.tictactoe.enums.GameStatus;
import com.dstork.tictactoe.exceptions.BadRequestException;
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
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.dstork.tictactoe.enums.GameStatus.*;
import static com.dstork.tictactoe.enums.PositionValue.*;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;

    Logger logger = LogManager.getLogger(GameServiceImpl.class);

    final GameMapper gameMapper;

    @Override
    public Optional<GameDTO> findGameById(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(" Game with Id: " + id + " Not Found."));

        logger.info("Game Details with Id : {}  Retrieved.", game.getId());
        return Optional.of(gameMapper.mapGameToGameDTO(game));
    }

    @Override
    public Optional<GameDTO> startGame(String playerLogin) {
        List<GameStatus> statusList = Arrays.asList(PENDING, O_TURN, X_TURN);

        Player player = playerRepository.findByLogin(playerLogin)
                .orElseThrow(() -> new ResourceNotFoundException("Player with Login " + playerLogin + "  Not Found"));

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
    public Optional<GameDTO> cancelGame(Long id) {
        Game game = gameRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Game with Id: %s Not Found",id)));

        if (isFinalGameStatus(game)) {
            throw new NotAllowedException(String.format("Game with Id : %s Can not be Cancelled",id));
        }
        game.setGameStatus(CANCELLED);
        game = gameRepository.save(game);
        logger.info("Game with Id:  {} is Cancelled", id);

        return Optional.of(gameMapper.mapGameToGameDTO(game));
    }

    @Override
    @Transactional( isolation = Isolation.READ_COMMITTED , propagation= Propagation.REQUIRED)
    public Optional<GameDTO> makeGameMove(GameMoveDTO gameMoveDTO) {

        Game game = gameRepository.findById(gameMoveDTO.getGameId()).orElseThrow(
                () -> new ResourceNotFoundException("Game with Id: " + gameMoveDTO.getGameId() + " Not Found"));

        checkGameMove(gameMoveDTO, game);

        if (game.getPlayerX().getLogin().equals(gameMoveDTO.getPlayerLogin())) {
            game.getGameBoard().put(gameMoveDTO.getPosition(), X);
            game.setGameStatus(O_TURN);
            logger.info("Player X Plays the position:  {}.", gameMoveDTO.getPosition());

        } else {
            game.getGameBoard().put(gameMoveDTO.getPosition(), O);
            game.setGameStatus(X_TURN);
            logger.info("Player O Plays the position {}.", gameMoveDTO.getPosition());

        }
        GameStatus gameStatus = GameUtil.checkBoardState(game.getGameBoard());
        if (gameStatus != null) {
            switch (gameStatus) {
                case X_WINS:
                    game.setGameStatus(X_WINS);
                    logger.info("Game ended the winner is  {}.", game.getPlayerX().getLogin());
                    break;
                case O_WINS:
                    game.setGameStatus(O_WINS);
                    logger.info("Game ended the winner is  {}.", game.getPlayerO().getLogin());
                    break;
                case DRAW:
                    game.setGameStatus(DRAW);
                    logger.info("Game ended , It is a draw");
                    break;
                default:
            }
        }

        game = gameRepository.save(game);
        GameUtil.printGameBoard(game.getGameBoard());

        return Optional.of(gameMapper.mapGameToGameDTO(game));
    }

    public void checkGameMove(GameMoveDTO gameMoveDTO, Game game) {

        if(playerRepository.findByLogin(gameMoveDTO.getPlayerLogin()).isEmpty()) {
            throw   new ResourceNotFoundException("Player with login " + gameMoveDTO.getPlayerLogin() + " Not found");
        }

        if (!isPlayerInGame(gameMoveDTO, game)) {
            throw new NotAllowedException("Player with login:  " + gameMoveDTO.getPlayerLogin() + " Is not in the game");
        }
        if (isGameDone(game)) {
            throw new NotAllowedException("Game with Id : " + game.getId() + " Already ended");
        }

        if (game.getGameStatus() == PENDING) {
            throw new NotAllowedException("Game with Id: " + game.getId() + " Is still waiting for the second player");
        }
        if (!isPlayerSTurn(game, gameMoveDTO)) {
            throw new BadRequestException("It is not the player:  " + gameMoveDTO.getPlayerLogin() + " 'S turn");
        }

        if (game.getGameStatus() == CANCELLED) {
            throw new NotAllowedException("Game with Id : " + game.getId() + " Is already cancellled");
        }

        if (game.getGameBoard().get(gameMoveDTO.getPosition()) != null) {
            throw new BadRequestException("The position " + gameMoveDTO.getPosition() + " Already filled in");
        }

    }

    private static boolean isPlayerInGame(GameMoveDTO gameMoveDTO, Game game) {
        return game.getPlayerX().getLogin().equals(gameMoveDTO.getPlayerLogin()) ||
                game.getPlayerO().getLogin().equals(gameMoveDTO.getPlayerLogin());
    }

    private boolean isPlayerSTurn(Game game, GameMoveDTO gameMoveDTO) {
        return (game.getPlayerX().getLogin().equals(gameMoveDTO.getPlayerLogin()) &&
                game.getGameStatus().equals(X_TURN) ||
                game.getPlayerO().getLogin().equals(gameMoveDTO.getPlayerLogin()) &&
                        game.getGameStatus().equals(O_TURN));
    }

    private static boolean isGameDone(Game game) {
        return game.getGameStatus() == X_WINS ||
                game.getGameStatus() == O_WINS ||
                game.getGameStatus() == DRAW;
    }


    private Game findGameByStatusPENDING() {
        return gameRepository.findByGameStatus(PENDING).stream().findAny().orElse(null);
    }

    private static boolean isEmptyList(List<Game> playerGames) {
        return playerGames == null || playerGames.isEmpty();
    }

    private static boolean isFinalGameStatus(Game game) {
        return game.getGameStatus() != PENDING && game.getGameStatus() != X_TURN && game.getGameStatus() != O_TURN;
    }

}

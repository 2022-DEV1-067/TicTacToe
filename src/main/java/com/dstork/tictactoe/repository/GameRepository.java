package com.dstork.tictactoe.repository;

import com.dstork.tictactoe.enums.GameStatus;
import com.dstork.tictactoe.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {

    List<Game> findByGameStatusInAndPlayerX_IdOrGameStatusInAndPlayerO_Id(List<GameStatus> statuses, Long playerXId, List<GameStatus> status, Long playerOId);

    List<Game> findByGameStatus(GameStatus status);
}

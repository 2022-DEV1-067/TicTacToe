package com.dstork.tictactoe.data;

import com.dstork.tictactoe.model.Player;
import com.dstork.tictactoe.repository.PlayerRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class InitPlayersData  implements ApplicationListener<ApplicationReadyEvent> {
    private final PlayerRepository playerRepository;

    public InitPlayersData(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        playerRepository.save(new Player("Zack X"));
        playerRepository.save(new Player("Sns O"));
        playerRepository.save(new Player("Alpha X"));
    }

}
package com.nahuel.GranDTFantasy.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.nahuel.GranDTFantasy.player.Player;

import java.util.List;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, String> {

    void deleteByName(String playerName);

    Optional<Player> findByName(String playerName);

    List<Player> findByTeamName(String teamName); 

    List<Player> findByPosition(String position); 
}

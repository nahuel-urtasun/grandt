package com.nahuel.GranDTFantasy.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.nahuel.GranDTFantasy.player.Player;
import com.nahuel.GranDTFantasy.repository.PlayerRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(PlayerRepository playerRepository){
        this.playerRepository = playerRepository;
    }

    public List<Player> getPlayers(){
        return playerRepository.findAll();
    }

    public List<Player> getPlayersByTeam(String teamName){
        return playerRepository.findByTeamName(teamName); // ¡Usa el método del repositorio!
    }

    public List<Player> getPlayersByName(String playerName){
        return playerRepository.findAll().stream()
                .filter(player -> player.getName().toLowerCase().contains(playerName.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayerByPosition(String playerPosition){
        return playerRepository.findByPosition(playerPosition); // ¡Usa el método del repositorio!
    }

    public List<Player> getPlayersByNationality(String nationality){
        return playerRepository.findAll().stream()
                .filter(player -> player.getNation().toLowerCase().contains(nationality.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Player> getPlayersByTeamAndPosition(String teamName, String position){
        // Puedes crear un método personalizado en el repositorio para esto si lo necesitas más adelante
        return playerRepository.findByTeamName(teamName).stream()
                .filter(player -> position.toLowerCase().contains(player.getPosition().toLowerCase()))
                .collect(Collectors.toList());
    }

    public Player addPlayer(Player player){
        playerRepository.save(player);
        return player;
    }

    public Player updatePlayer(Player updatePlayer){
        Optional<Player> existingPlayer = playerRepository.findByName(updatePlayer.getName());
        if (existingPlayer.isPresent()){
            Player playerToUpdate = existingPlayer.get();
            playerToUpdate.setName(updatePlayer.getName());
            playerToUpdate.setPosition(updatePlayer.getPosition());
            playerToUpdate.setNation(updatePlayer.getNation());
            playerToUpdate.setTeam_name(updatePlayer.getTeam_name());

            playerRepository.save(playerToUpdate);
            return playerToUpdate;
        }
        return null;
    }

    @Transactional
    public void deletePlayer(String playerName){
        playerRepository.deleteByName(playerName);
    }
}

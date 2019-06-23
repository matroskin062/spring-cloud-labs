package com.restservice.restservice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restservice.restservice.Messaging.Producer;
import com.restservice.restservice.Model.Log;
import com.restservice.restservice.Model.Player;
import com.restservice.restservice.Model.Player;
import com.restservice.restservice.Model.Team;
import com.restservice.restservice.Repository.PlayerRepository;
import com.restservice.restservice.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Component
@Service
public class PlayerService {
    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    Producer producer;

    @Value("${jsa.rabbitmq.queue.createdtype}")
    String queueCreatedName;

    @Value("${jsa.rabbitmq.queue.updatedtype}")
    String queueUpdatedName;

    @Value("${jsa.rabbitmq.queue.deletedtype}")
    String queueDeletedName;

    public Iterable<Player> getAll() {
        return playerRepository.findByPresenceFlagTrue();
    }

    public Player getObjectById(int id) throws Exception {
        Optional<Player> player = playerRepository.findByIdAndPresenceFlagTrue(id);
        if (!player.isPresent())
            throw new Exception("No item with this id");
        if(!player.get().isPresenceFlag())
            throw new Exception("No item with this id");
        return player.get();
    }

    public Player saveObject(Player player) throws Exception {
        if(player.getPlayerName() == null) throw new Exception("You should to input username");

        try {
            Team team = teamRepository.findById(player.getTeam().getId()).get();

            Player newPlayer = new Player(team, player.getPlayerName(), player.getAge(),
                    player.getFieldPos(), player.getGoals(), player.getCards());

            sendLog(newPlayer, queueCreatedName);
            return playerRepository.save(newPlayer);
        }
        catch (Exception e) {
            throw new Exception("Item was not created");
        }
    }

    public Boolean deleteObject(int id) throws Exception {
        try{
            Player newObject = getObjectById(id);

            newObject.setPresenceFlag(false);

            sendLog(newObject, queueDeletedName);
            playerRepository.save(newObject);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public Player updateObject(Player player, int id) throws Exception {
        if(player.getPlayerName() == null) throw new Exception("You should to input username");
        try {
            Player updatedPlayer = getObjectById(id);

            Team team = teamRepository.findById(player.getTeam().getId()).get();

            updatedPlayer.setTeam(team);
            updatedPlayer.setPlayerName(player.getPlayerName());
            updatedPlayer.setAge(player.getAge());
            updatedPlayer.setFieldPos(player.getFieldPos());
            updatedPlayer.setGoals(player.getGoals());
            updatedPlayer.setCards(player.getCards());

            sendLog(updatedPlayer, queueUpdatedName);
            playerRepository.save(updatedPlayer);

            return updatedPlayer;
        }
        catch (Exception e){
            throw new Exception("Item was not updated");
        }
    }

    private void sendLog(Player player, String queueName) {
        System.out.println("*******************");
        System.out.println("Sending message");
        Log logRecord = new Log();
        ObjectMapper mapper = new ObjectMapper();


        try {
            logRecord.setMessageText(mapper.writeValueAsString(player));
            logRecord.setEntityName(Player.class.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

        String loggerRecordString = null;
        try {
            loggerRecordString = mapper.writeValueAsString(logRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(loggerRecordString);
        producer.produceMsg(loggerRecordString, queueName);
    }
}

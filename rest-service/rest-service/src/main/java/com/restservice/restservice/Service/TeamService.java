package com.restservice.restservice.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restservice.restservice.Messaging.Producer;
import com.restservice.restservice.Model.Log;
import com.restservice.restservice.Model.Team;
import com.restservice.restservice.Repository.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class TeamService {
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


    public Iterable<Team> getAll() {
        return teamRepository.findByPresenceFlagTrue();
    }

    public Team getObjectById(Integer id) throws Exception{
        Optional<Team> team = teamRepository.findByIdAndPresenceFlagTrue(id);
        if (!team.isPresent())
            throw new Exception("No item with this id");

        return team.get();

    }

    public Team saveObject(Team newObject) throws Exception {
        if(newObject.getTeamName() == null){
            throw new Exception("Title can't be null");
        }else if(newObject.getCoach() == null){
            throw new Exception("Author can't be null");
        }else{

            sendLog(newObject, queueCreatedName);
            return teamRepository.save(newObject);
        }
    }

    public Boolean deleteObject(int id) throws Exception {
        try{
            Team newObject = getObjectById(id);

            newObject.setPresenceFlag(false);

            sendLog(newObject, queueDeletedName);
            teamRepository.save(newObject);
        } catch (Exception e) {
            throw e;
        }
        return true;
    }

    public Team updateObject(Team newObject, int id) throws Exception {
        if(newObject.getTeamName() == null){
            throw new Exception("You should to input title");
        }else if(newObject.getPosition() == 0){
            throw new Exception("You should to input author");
        }else {

            try {
                Team updatedTeam = getObjectById(id);

                updatedTeam.setTeamName(newObject.getTeamName());
                updatedTeam.setCoach(newObject.getCoach());
                updatedTeam.setLeague(newObject.getLeague());
                updatedTeam.setPosition(newObject.getPosition());

                sendLog(updatedTeam, queueUpdatedName);
                teamRepository.save(updatedTeam);

                return updatedTeam;
            } catch (Exception e) {
                throw new Exception("Item was not updated");
            }
        }
    }

    private void sendLog(Team team, String queueName) {
        System.out.println("*******************");
        System.out.println("Sending message");
        Log logRecord = new Log();
        ObjectMapper mapper = new ObjectMapper();


        try {
            logRecord.setMessageText(mapper.writeValueAsString(team));
            logRecord.setEntityName(Team.class.getName());
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

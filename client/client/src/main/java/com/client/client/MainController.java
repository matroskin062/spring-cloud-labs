package com.client.client;

import com.client.client.DTO.Player;
import com.client.client.DTO.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;


@RestController
public class MainController {
    @Bean
    RestTemplate restTemplate(){
        return new RestTemplate();
    }

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private LoadBalancerClient client;

    private static Logger log = LoggerFactory.getLogger(ClientApplication.class);
    @RequestMapping(value = "/instances")
    public String getInstancesRun(){
        ServiceInstance instance = client.choose("rest_service");
        return instance.getUri().toString();
    }

    @RequestMapping(value = "/teams", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public String getTeam() {
        String url = getInstancesRun();
        log.info("teams from " + url);
        String response = this.restTemplate.exchange(String.format("%s/teams/", url),
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                }).getBody();

        return response;
    }

    @RequestMapping(value = "/teams/{id}", method = RequestMethod.GET)
    public String getTeamById(@PathVariable Integer id) {
        String url = getInstancesRun();

        String response = this.restTemplate.exchange(String.format("%s/teams/" + id, url),
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                }).getBody();

        return response;
    }

    @RequestMapping(value = "/teams/delete/{id}", method = RequestMethod.GET, produces = "application/json; charset=UTF-8")
    public String deleteTeam(@PathVariable Integer id) {
        String url = getInstancesRun();

        String response = this.restTemplate.exchange(String.format("%s/teams/delete/" + id, url),
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                }).getBody();

        return response;
    }

    @RequestMapping(value = "/teams/update/{id}", method = RequestMethod.POST)
    public String updateTeam(@PathVariable Integer id, @RequestBody Team team) {
        String url = getInstancesRun();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Team> entity= new HttpEntity<>(team);


        String response = this.restTemplate.exchange(String.format("%s/teams/update/" + id, url),
                HttpMethod.POST, entity, new ParameterizedTypeReference<String>() {
                }).getBody();

        return response;
    }

    @RequestMapping(value = "/teams/create", method = RequestMethod.POST, produces = "application/json; charset=UTF-8")
    public String createTeam(@RequestBody Team team) {
        String url = getInstancesRun();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Team> entity= new HttpEntity<>(team);

        String response = this.restTemplate.exchange(String.format("%s/teams/create/", url),
                HttpMethod.POST, entity, new ParameterizedTypeReference<String>() {
                }).getBody();

        return response;
    }

    @RequestMapping(value = "/players", method = RequestMethod.GET)
    public String getPlayers() {
        String url = getInstancesRun();

        String response = this.restTemplate.exchange(String.format("%s/players/", url),
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                }).getBody();

        return response;
    }

    @RequestMapping(value = "/players/{id}", method = RequestMethod.GET)
    public String getPlayersById(@PathVariable Integer id) {
        String url = getInstancesRun();

        String response = this.restTemplate.exchange(String.format("%s/players/" + id, url),
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                }).getBody();

        return response;
    }

    @RequestMapping(value = "/players/create", method = RequestMethod.POST)
    public String createPlayers(@RequestBody Player player) {
        String url = getInstancesRun();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Player> entity = new HttpEntity<>(player);

        String response = this.restTemplate.exchange(String.format("%s/players/create", url),
                HttpMethod.POST, entity, new ParameterizedTypeReference<String>() {
                }).getBody();

        return response;
    }

    @RequestMapping(value = "/players/delete/{id}", method = RequestMethod.GET)
    public String deletePlayers(@PathVariable Integer id) {
        String url = getInstancesRun();

        String response = this.restTemplate.exchange(String.format("%s/players/delete/" + id, url),
                HttpMethod.GET, null, new ParameterizedTypeReference<String>() {
                }).getBody();

        return response;
    }

    @RequestMapping(value = "/players/update/{id}", method = RequestMethod.POST)
    public String updatePlayers(@PathVariable Integer id, @RequestBody Player player) {
        String url = getInstancesRun();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Player> entity = new HttpEntity<>(player);

        String response = this.restTemplate.exchange(String.format("%s/players/update/" + id, url),
                HttpMethod.POST, entity, new ParameterizedTypeReference<String>() {
                }).getBody();

        return response;
    }
}

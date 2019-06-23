package com.restservice.restservice.Controller;

import com.restservice.restservice.Model.Player;
import com.restservice.restservice.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/players")
public class PlayerController {
    @Autowired
    private PlayerService service;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getAll() {
        try {
            return new ResponseEntity(service.getAll(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable("id") Integer id) {
        if(id <= 0 ) return new ResponseEntity("Id have to be more than 0", HttpStatus.BAD_REQUEST);
        try {
            return new ResponseEntity(service.getObjectById(id), HttpStatus.OK);

        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Player player){
        if(player.getTeam().getId() == 0 || player.getTeam().getId() == null) return new ResponseEntity("You should input team", HttpStatus.BAD_REQUEST);

        try{
            return new ResponseEntity(service.saveObject(player), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        if(id <= 0) return new ResponseEntity("Id have to be more than 0", HttpStatus.BAD_REQUEST);

        try{
            return new ResponseEntity(service.deleteObject(id), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    public  ResponseEntity<?> update(@RequestBody Player newPlayer, @PathVariable Integer id){
        if(id <= 0) return new ResponseEntity("Id have to be more than 0", HttpStatus.BAD_REQUEST);
        if(newPlayer.getTeam().getId() == 0 || newPlayer.getTeam().getId() == null) return new ResponseEntity("You should input teamId", HttpStatus.BAD_REQUEST);

        try{
            return new ResponseEntity(service.updateObject(newPlayer, id), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

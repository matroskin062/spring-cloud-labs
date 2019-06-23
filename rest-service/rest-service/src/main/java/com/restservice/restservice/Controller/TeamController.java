package com.restservice.restservice.Controller;

import com.restservice.restservice.Model.Team;
import com.restservice.restservice.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/teams")
public class TeamController {
    @Autowired
    private TeamService service;

    @GetMapping
    public ResponseEntity<?> getAll(){
        try {
            return new ResponseEntity(service.getAll(), HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> getById(@PathVariable Integer id){
        if(id <= 0 || id.toString() == null) return new ResponseEntity("Id have to be more than 0", HttpStatus.BAD_REQUEST);
        try {
            return new ResponseEntity(service.getObjectById(id), HttpStatus.OK);

        }catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
    @RequestMapping(path = "/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody Team team){
        try {
            return new ResponseEntity(service.saveObject(team), HttpStatus.OK);
        }catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(path = "/delete/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> delete(@PathVariable Integer id){
        if(id <= 0) return new ResponseEntity("Id have to be more than 0", HttpStatus.BAD_REQUEST);
        try {
            return new ResponseEntity(service.deleteObject(id), HttpStatus.OK);
        }
        catch (Exception ex){
            return new ResponseEntity(ex.getMessage(), HttpStatus.NOT_FOUND);
        }

    }

    @RequestMapping(path = "/update/{id}", method = RequestMethod.POST)
    public ResponseEntity<?> update(@RequestBody Team newTeam, @PathVariable int id){
        if(id <= 0) return new ResponseEntity("Id have to be more than 0", HttpStatus.BAD_REQUEST);
        try{
            return new ResponseEntity(service.updateObject(newTeam, id), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

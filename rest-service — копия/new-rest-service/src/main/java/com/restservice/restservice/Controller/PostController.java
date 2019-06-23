package com.restservice.restservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.restservice.restservice.Model.Post;
import com.restservice.restservice.Service.PostService;
import org.springframework.web.servlet.mvc.method.annotation.PathVariableMapMethodArgumentResolver;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping(value = "/posts")
public class PostController {
    @Autowired
    private PostService service;

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
    public ResponseEntity<?> create(@RequestBody Post post){
       try {
           return new ResponseEntity(service.saveObject(post), HttpStatus.OK);
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
    public ResponseEntity<?> update(@RequestBody Post newPost, @PathVariable int id){
        if(id <= 0) return new ResponseEntity("Id have to be more than 0", HttpStatus.BAD_REQUEST);
        try{
            return new ResponseEntity(service.updateObject(newPost, id), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

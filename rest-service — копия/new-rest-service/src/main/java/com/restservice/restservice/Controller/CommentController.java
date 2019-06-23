package com.restservice.restservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.restservice.restservice.Model.Comment;
import com.restservice.restservice.Service.CommentService;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService service;

    @RequestMapping(method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getAll() {
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
    public ResponseEntity<?> create(@RequestBody Comment comment){
        if(comment.getPost().getId() == 0) return new ResponseEntity("You should input brandId", HttpStatus.BAD_REQUEST);

        try{
            return new ResponseEntity(service.saveObject(comment), HttpStatus.OK);
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
    public  ResponseEntity<?> update(@RequestBody Comment newComment, @PathVariable Integer id){
        if(id <= 0) return new ResponseEntity("Id have to be more than 0", HttpStatus.BAD_REQUEST);
        if(newComment.getPost().getId() == 0 || newComment.getPost().getId() == null) return new ResponseEntity("You should input brandId", HttpStatus.BAD_REQUEST);

        try{
            return new ResponseEntity(service.updateObject(newComment, id), HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

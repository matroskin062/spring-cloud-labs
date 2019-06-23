package com.restservice.restservice.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.restservice.restservice.Messaging.Producer;
import com.restservice.restservice.Model.Log;
import com.restservice.restservice.Repository.LogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/logs")
public class MessageController {
    @Autowired
    Producer publisher;

    @Autowired
    private LogRepository repository;

    @RequestMapping("/send")
    public String sendMessage(){
        System.out.println("*******************");
        Log logRecord = new Log();
        logRecord.setMessageText("TESTCREATAE");
        logRecord.setMessageType("le");
        ObjectMapper mapper = new ObjectMapper();

        //Convert object to JSON string and pretty print
        String jsonInString = null;
        try {
            jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(logRecord);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(jsonInString);
        publisher.produceMsg("jsa.queue2", jsonInString);
        return "Successfully Msg Sent";
    }

    @RequestMapping
    public @ResponseBody
    ResponseEntity<?> getAll(){
        try{
            return ResponseEntity.status(HttpStatus.OK).body(repository.findAll());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}

package com.consumer.consumer;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class Subscriber {

    @RabbitListener(queues="${jsa.rabbitmq.queue.createdtype}")
    public void receivedMessageCreated(String msg) {
        System.out.println("Received Message: " + msg);

        Log record = DeserializeLogRecord(msg);
        System.out.println(record);

        if(record == null) return;
        record.setMessageType("RECORD CREATED");
        saveEntity(record);
    }

    @RabbitListener(queues="${jsa.rabbitmq.queue.deletedtype}")
    public void receivedMessageDeleted(String msg) {
        System.out.println("Received Message: " + msg);

        Log record = DeserializeLogRecord(msg);
        System.out.println(record);

        if(record == null) return;
        record.setMessageType("RECORD DELETED");
        saveEntity(record);
    }

    @RabbitListener(queues="${jsa.rabbitmq.queue.updatedtype}")
    public void receivedMessageUpdated(String msg) {
        System.out.println("Received Message: " + msg);

        Log record = DeserializeLogRecord(msg);
        System.out.println(record);

        if(record == null) return;
        record.setMessageType("RECORD UPDATED");
        saveEntity(record);
    }

    private Log DeserializeLogRecord(String logRecord)
    {
        ObjectMapper mapper = new ObjectMapper();
        try {
            Log LogEntity = mapper.readValue(logRecord, Log.class);
            System.out.println(LogEntity);
            return  LogEntity;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  null;
    }

    @Autowired
    private LogRepository repository;

    public Log saveEntity(Log table) {

        if (table.getMessageText() == null ||
                table.getMessageType() == null) {
            return null;
        }
        return repository.save(table);
    }
}

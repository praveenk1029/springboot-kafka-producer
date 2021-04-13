package com.practice.springbootkafkaproducer.controller;

import com.practice.springbootkafkaproducer.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;
    //private static final String TOPIC="KafkaExample";
    //private static final String TOPIC="KafkaExampleJson";
    //private static final String TOPIC="KafkaJson";
    private static final String TOPIC="DemoTopic";

    @GetMapping("/publish/{name}")
    public String postMessage(@PathVariable final String name){
        kafkaTemplate.send(TOPIC, new Person(name,"Detroit", 25, 9890.24));
        return "Published the Json message successfully!";
    }

    @PostMapping("/publish")
    public String sendMessage(@RequestBody Person person){
        kafkaTemplate.send(TOPIC, person);
        return "Published the Json message successfully!";
    }

    //Kafka Server Properties
    /*C:\kafka_2.13-2.4.0\bin\windows>zookeeper-server-start.bat ..\..\config\zookeeper.properties
    C:\kafka_2.13-2.4.0\bin\windows>kafka-server-start.bat ..\..\config\server.properties
    C:\kafka_2.13-2.4.0>.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic KafkaExample
    C:\kafka_2.13-2.4.0>bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic KafkaExample --from-beginning*/

}

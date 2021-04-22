package com.practice.springbootkafkaproducer.controller;

import com.practice.springbootkafkaproducer.model.Person;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.web.bind.annotation.*;
import java.util.Properties;
import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    Logger logger = LoggerFactory.getLogger(KafkaController.class);
    @Autowired
    private KafkaTemplate<String, Person> kafkaTemplate;
    @Value("${spring.kafka.producer.topic}")
    private String TOPIC;
    //private static final String TOPIC="KafkaExample";
    //private static final String TOPIC="KafkaExampleJson";
    //private static final String TOPIC="KafkaJson";
    //private static final String TOPIC="DemoTopic";
   // private static final String TOPIC="MultiPartitionTopic";

    @GetMapping("/publish/{name}")
    public String postMessage(@PathVariable final String name){
        kafkaTemplate.send(TOPIC, new Person(name,"Detroit", 25, 9890.24));
        return "Published the Json message successfully!";
    }

    @PostMapping("/publish")
    public String sendMessage(@RequestBody Person person){
        //kafkaTemplate.send("${spring.kafka.producer.topic}", person);
        try {
            SendResult<String, Person> sentResult = kafkaTemplate.send(TOPIC, person).get();
            RecordMetadata recordMetadata =sentResult.getRecordMetadata();
            logger.info("Topic: "+recordMetadata.topic()+"\n"
                    +"Partition: "+recordMetadata.partition()+"\n"
                    +"Offset: "+recordMetadata.offset()+"\n"
                    +"Timestamp: "+recordMetadata.timestamp()
            );
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return "Published the Json message successfully!";
    }

    /*@PostMapping("/publish")
    public String sendMessage(@RequestBody Person person){
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092, localhost:9093, localhost:9094");
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());
        KafkaProducer<String, Person> kafkaProducer = new KafkaProducer<String, Person>(properties);
        ProducerRecord<String, Person> producerRecord = new ProducerRecord<>("Replication3Topic", person);
        kafkaProducer.send(producerRecord, new Callback(){
            public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                if(e == null){
                    logger.info("Topic: "+recordMetadata.topic()+"\n"
                            +"Partition: "+recordMetadata.partition()+"\n"
                            +"Offset: "+recordMetadata.offset()+"\n"
                            +"Timestamp: "+recordMetadata.timestamp()
                    );
                }else{
                    logger.error("Error while sending to Topic:", e);
                }
            }
        });
        return "Published the Json message successfully!";
    }*/

    //Kafka Server Properties
    /*C:\kafka_2.13-2.4.0\bin\windows>zookeeper-server-start.bat ..\..\config\zookeeper.properties
    C:\kafka_2.13-2.4.0\bin\windows>kafka-server-start.bat ..\..\config\server.properties
    C:\kafka_2.13-2.4.0>.\bin\windows\kafka-topics.bat --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic KafkaExample
    C:\kafka_2.13-2.4.0>bin\windows\kafka-console-consumer.bat --bootstrap-server localhost:9092 --topic KafkaExample --from-beginning*/

}

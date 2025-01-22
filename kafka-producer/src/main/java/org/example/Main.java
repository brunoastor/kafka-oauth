package org.example;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.example.config.ConfigManager;
import org.example.config.ProducerCreator;

import java.util.concurrent.ExecutionException;

public class Main {

    public static void main(String[] args) {
        runProducer();
    }

    static void runProducer() {

        Producer<Long, String> producer = ProducerCreator.createProducer();

        for (int index = 0; index < 10; index++) {

            final ProducerRecord<Long, String> record = new ProducerRecord<Long, String>(
                    ConfigManager.getProperty("oauth.server.topic"),
                    "This is record " + index);
            try {
                RecordMetadata metadata = producer.send(record).get();
                System.out.println("Record sent with key " + index
                        + " to partition " + metadata.partition()
                        + " with offset " + metadata.offset());
            } catch (ExecutionException e) {
                System.out.println("Error in sending record");
                System.out.println(e);
            } catch (InterruptedException e) {
                System.out.println("Error in sending record");
                System.out.println(e);
            }
        }
        producer.flush();
        producer.close();
    }
}
package com.dectfix.memorylorry.KafkaDemo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

/**
 * # Automatic Offset Committing
 * This example demonstrates a simple usage of Kafka's consumer api that relies on automatic offset committing.
 */
public class AutoCommitConsumer {
    public static void main(String[] args) throws IOException {
        //读取conf中的配置，创建输入流
        InputStream conf = AutoCommitConsumer.class.getClass().getResourceAsStream("/autocommit-kafka.properties");
        Properties props = new Properties();
        props.load(conf);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                System.out.printf(
                        "offset = %d, key = %s, value = %s,timestamp = %s %n",
                        record.offset(),
                        record.key(),
                        record.value(),
                        record.timestamp()
                );
            }

        }
    }

}

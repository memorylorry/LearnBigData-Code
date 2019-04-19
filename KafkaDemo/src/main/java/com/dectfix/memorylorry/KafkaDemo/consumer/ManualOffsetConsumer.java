package com.dectfix.memorylorry.KafkaDemo.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;

public class ManualOffsetConsumer {
    public static void main(String[] args) throws IOException {
        InputStream conf = ManualOffsetConsumer.class.getResourceAsStream("/manualoffset-kafka.properties");

        Properties props = new Properties();
        props.load(conf);

        KafkaConsumer<String,String> consumer = new KafkaConsumer<>(props);
        consumer.subscribe(Arrays.asList("test"));
        final int minBatchSize = 20;
        List<ConsumerRecord<String, String>> buffer = new ArrayList<>();
        while (true) {
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }

            if (buffer.size() >= minBatchSize) {
                //insertIntoDb(buffer);
                consumer.commitSync();// manual submit offset
                buffer.clear();
            }
        }

    }
}

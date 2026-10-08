package com.example.FileGenerator.EnvFile.Configuration;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {
    @Autowired
    Environment environment;



    private final String ScriptCommandsTopicName;

    public KafkaConfig(@Value("${script.commands.topic.name}") String scriptCommandsTopicName) {
        ScriptCommandsTopicName = scriptCommandsTopicName;
    }

    Map<String,Object> Configuration(){
    Map<String,Object> config = new HashMap<>();
        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,environment.getProperty("spring.kafka.bootstrap-servers"));
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,environment.getProperty("spring.kafka.producer.key-serializer"));
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,environment.getProperty("spring.kafka.producer.value-serializer"));
                config.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,environment.getProperty("spring.kafka.producer.properties.max.in.flight.requests.per.connection"));
        config.put(ProducerConfig.RETRIES_CONFIG,environment.getProperty("spring.kafka.producer.retries"));
        config.put(ProducerConfig.ACKS_CONFIG,environment.getProperty("spring.kafka.producer.acks"));
        config.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,environment.getProperty("spring.kafka.producer.properties.delivery.timeout.ms"));
        config.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,environment.getProperty("spring.kafka.producer.properties.enable.idempotence"));
                        return config;
}
@Bean
    ProducerFactory producerFactory(){
        return new DefaultKafkaProducerFactory(Configuration());
}
    @Bean
 KafkaTemplate<String,String> kafkaTemplate(){
     return new KafkaTemplate(producerFactory());
 }

 NewTopic createScript(){
        return TopicBuilder.name(ScriptCommandsTopicName)
                .replicas(3).partitions(3).build();
 }

}

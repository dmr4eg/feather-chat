package nss.be.messages.config

import nss.be.messages.model.Message
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.ConsumerFactory
import java.util.*

@Configuration
@EnableKafka
class KafkaConfig (private val consumerFactory: ConsumerFactory<UUID, Message>){

    @Bean
    fun kafkaConsumer(): KafkaConsumer<UUID, Message> {
        return KafkaConsumer(consumerFactory.configurationProperties)
    }
}

package nss.be.messages.config

import nss.be.messages.model.MESSAGE_TOPIC_NAME
import nss.be.messages.model.Message
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.core.KafkaAdmin
import org.springframework.kafka.core.KafkaAdmin.NewTopics
import java.util.*
import java.util.Map
import kotlin.collections.List
import kotlin.collections.map
import kotlin.collections.toMutableList


@Configuration
class KafkaTopicConfig {
    @Bean
    fun topics(): NewTopics = NewTopics(
        TopicBuilder.name(MESSAGE_TOPIC_NAME)
            .partitions(1)
            .replicas(1)
            .build()
    )
}

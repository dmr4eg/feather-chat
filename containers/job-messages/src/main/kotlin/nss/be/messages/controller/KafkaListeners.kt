package nss.be.messages.controller

import nss.be.messages.model.MESSAGE_TOPIC_NAME
import nss.be.messages.model.Message
import nss.be.messages.service.MessageService
import nss.be.messages.service.impl.MessageServiceImpl
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.*

@Component
class KafkaListeners(
    @Autowired
    private val messageService: MessageService
) {

    private val logger = LoggerFactory.getLogger(KafkaListeners::class.java)

    @KafkaListener(topics = [MESSAGE_TOPIC_NAME])
    fun listen(record: ConsumerRecord<UUID, Message>) {
        logger.info("Received message")
        val message: Message = record.value()
        when (message.state) {
            Message.State.SENT -> {
                messageService.save(message)
            }
            Message.State.RECEIVED -> {
                messageService.makeReceived(message)
            }
            Message.State.READ -> {
                messageService.makeRead(message)
            }
            Message.State.EDITED -> {
                messageService.edit(message)
            }
        }
        logger.info("Message processed")
    }

}
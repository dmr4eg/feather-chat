package nss.be.messages.service.impl

import nss.be.messages.model.Message
import nss.be.messages.repository.MessageRepository
import nss.be.messages.service.MessageService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service


@Service
class MessageServiceImpl(
    private val messageRepository: MessageRepository
) : MessageService {

    var logger: Logger = LoggerFactory.getLogger(MessageServiceImpl::class.java)


    override fun save(message: Message) {
        messageRepository.save(message)
        logger.info("Message saved")
    }

    override fun makeRead(message: Message) {
        messageRepository.findById(message.id)
            .orElse(null)?.let {
                val updatedMessage = it.copy(state = Message.State.READ)
                messageRepository.save(updatedMessage)
                logger.info("Message read")
            }
    }

    override fun makeReceived(message: Message) {
        logger.info("Making message received")
        messageRepository.findById(message.id)
            .orElse(null)?.let {
                val updatedMessage = it.copy(state = Message.State.RECEIVED)
                messageRepository.save(updatedMessage)
                logger.info("Message received")
            }
    }

    override fun edit(message: Message) {
        logger.info("Making message edited")
        messageRepository.findById(message.id)
            .orElse(null)?.let {
                if(message.text == it.text) {
                    return
                }
                val updatedMessage = it.copy(state = Message.State.EDITED, text = message.text)
                messageRepository.save(updatedMessage)
                logger.info("Message edited")
            }
    }
}

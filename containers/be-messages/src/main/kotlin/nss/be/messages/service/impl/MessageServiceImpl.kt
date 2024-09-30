package nss.be.messages.service.impl

import nss.be.messages.model.MESSAGE_TOPIC_NAME
import nss.be.messages.model.Message
import nss.be.messages.model.MessageId
import nss.be.messages.repository.MessageReadOnlyRepository
import nss.be.messages.service.MessageService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import okhttp3.OkHttpClient
import okhttp3.Request
import org.apache.kafka.clients.consumer.KafkaConsumer
import org.json.JSONObject
import org.slf4j.LoggerFactory
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import java.io.IOException
import java.time.Duration
import java.util.*


@Service
class MessageServiceImpl(
    @Autowired private val kafkaTemplate: KafkaTemplate<UUID, Message>,
    private val kafkaConsumer: KafkaConsumer<UUID, Message>,
    private val messageReadOnlyRepository: MessageReadOnlyRepository
) : MessageService {
    private val logger = LoggerFactory.getLogger(MessageServiceImpl::class.java)

    init {
        kafkaConsumer.subscribe(listOf(MESSAGE_TOPIC_NAME))
    }

    override fun queryMessages(topic: String, pollDuration: Long): List<Message> {
        val records = kafkaConsumer.poll(Duration.ofMillis(pollDuration))
        val messages = mutableListOf<Message>()
        for (record in records) {
            val message = record.value()
            messages.add(message)
        }
        logger.info("Queried ${messages.size} messages")
        return messages
    }

    private fun handleFailure(message: String, topic: String, offset: Long) {
        logger.error("Failed message: $message from $topic @ $offset")
    }

    override fun shutdown() {
        kafkaConsumer.close()
    }

    private fun getTimeFromApi(): Long {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("http://worldtimeapi.org/api/timezone/Etc/UTC")
            .build()
        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")
            val responseData = response.body?.string() ?: throw IOException("No response body")
            val json = JSONObject(responseData)
            logger.info("Got time from API: ${json.getLong("unixtime")}")
            return json.getLong("unixtime") * 1000
        }
    }

    override fun sendMessage(message: Message, ) {
        val apiTime: Long = getTimeFromApi()
        if (message.id.timestamp > apiTime) {
            logger.error("Message timestamp is in the future")
            throw IllegalArgumentException("Message timestamp is in the future")
        }
        message.id.timestamp = apiTime
        logger.info("Sending message: $message")
        kafkaTemplate.send(message.toKafkaRecord(MESSAGE_TOPIC_NAME))
    }


    override fun getMessageById(id: MessageId): Message? {
        val kafkaMessages = queryMessages(MESSAGE_TOPIC_NAME, 1000)
        val kafkaMessage = kafkaMessages.find { it.id == id }
        val pgMessage = messageReadOnlyRepository.findById(id).orElse(null)
        logger.info("Retrieved message: $id")
        return kafkaMessage ?: pgMessage
    }

    override fun editMessage(newMessage: Message) {
        newMessage.state = Message.State.EDITED
        kafkaTemplate.send(newMessage.toKafkaRecord(MESSAGE_TOPIC_NAME))
        logger.info("Edited message: $newMessage")
    }

    override fun deleteMessageById(id: MessageId){
        val newMessage = Message(id = id, state = Message.State.EDITED, text = "")
        editMessage(newMessage)
        logger.info("Deleted message: $id")
    }

    override fun getMessages(chatUUID: UUID, limit: Int, offset: Int): List<Message> {
        val kafkaMessages:List<Message> = queryMessages(MESSAGE_TOPIC_NAME, 1000)
            .filter { it.id.chatUUID == chatUUID }
            .sortedBy { it.id.timestamp }
        val pgMessages:List<Message> = messageReadOnlyRepository.findById_ChatUUID(
            chatUUID,
            PageRequest.of(offset, limit, Sort.by("timestamp"))
        )
        val messages = (kafkaMessages + pgMessages).sortedBy { it.id.timestamp }.take(limit)
        logger.info("Retrieved ${messages.size} messages for chat $chatUUID")
        return messages
    }
}

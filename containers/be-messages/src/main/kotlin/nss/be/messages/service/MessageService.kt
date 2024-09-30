package nss.be.messages.service

import nss.be.messages.model.Message
import nss.be.messages.model.MessageId
import java.util.*

/**
 * Service interface for managing and interacting with messages.
 * This interface provides methods for querying, sending, editing, deleting, and retrieving messages
 * from both Kafka and the PostgreSQL database.
 */
interface MessageService {
    /**
     * Queries messages from the given Kafka topic within a specific poll duration.
     *
     * @param topic the name of the Kafka topic to query
     * @param pollDuration the duration (in milliseconds) to poll the topic for messages
     * @return a list of [Message] objects retrieved from the topic
     */
    fun queryMessages(topic: String, pollDuration: Long): List<Message?>?

    /**
     * Shuts down the Kafka consumer, closing all related resources.
     */
    fun shutdown()

    /**
     * Sends a message to the Kafka topic.
     *
     * @param message the [Message] to be sent
     * @throws IllegalArgumentException if the message's timestamp is in the future
     */
    fun sendMessage(message: Message)

    /**
     * Retrieves a message by its unique [MessageId].
     *
     * @param id the unique identifier of the message to retrieve
     * @return the [Message] if found, otherwise `null`
     */
    fun getMessageById(id: MessageId): Message?

    /**
     * Edits an existing message by marking its state as `EDITED` and sending it to Kafka.
     *
     * @param newMessage the updated [Message] to be sent
     */
    fun editMessage(newMessage: Message)

    /**
     * Deletes a message by its unique [MessageId], setting its text to an empty string and marking it as edited.
     *
     * @param id the unique identifier of the message to delete
     */
    fun deleteMessageById(id: MessageId)

    /**
     * Retrieves a list of messages for a specific chat, combining results from both Kafka and PostgreSQL.
     *
     * @param chatUUID the unique identifier of the chat
     * @param limit the maximum number of messages to retrieve
     * @param offset the offset for pagination
     * @return a list of [Message] objects sorted by timestamp
     */
    fun getMessages(chatUUID: UUID, limit: Int, offset: Int): List<Message>
}
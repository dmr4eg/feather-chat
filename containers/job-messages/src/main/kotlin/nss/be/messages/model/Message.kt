package nss.be.messages.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.EmbeddedId
import jakarta.persistence.Entity
import jakarta.persistence.Table
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.common.header.internals.RecordHeader
import java.util.*
import javax.validation.Valid
import javax.validation.constraints.NotNull

const val MESSAGE_TOPIC_NAME: String = "feather-message"

/**
 * Message object
 * @param id
 * @param state Current state of the message
 * @param text Text of the message
 */
@Entity
@Table(name = "message")
data class Message(
    @EmbeddedId
    @NotNull
    @field:Valid
    @Schema(example = "null", required = true, description = "")
    @get:JsonProperty("id", required = true)
    val id: MessageId,

    @NotNull
    @Schema(example = "RECEIVED", required = true, description = "Current state of the message")
    @get:JsonProperty("state", required = true)
    var state: State,

    @NotNull
    @Schema(example = "Hello, world!", required = true, description = "Text of the message")
    @get:JsonProperty("text", required = true)
    val text: kotlin.String
) {

    /**
     * Current state of the message
     * Values: SENT,RECEIVED,READ,EDITED
     */
    enum class State(val value: kotlin.String) {
        @JsonProperty("SENT") SENT("SENT"),
        @JsonProperty("RECEIVED") RECEIVED("RECEIVED"),
        @JsonProperty("READ") READ("READ"),
        @JsonProperty("EDITED") EDITED("EDITED")
    }


    fun toKafkaRecord(topic: String): ProducerRecord<UUID, Message> {
        val record = ProducerRecord<UUID, Message>(
            topic,
            id.chatUUID,
            this
        )
        // add chatId header
        record.headers().add(RecordHeader("chatId", id.chatUUID.toString().toByteArray()))
        record.headers().add(RecordHeader("senderId", id.senderUUID.toString().toByteArray()))
        record.headers().add(RecordHeader("timestamp", id.timestamp.toString().toByteArray()))
        record.headers().add(RecordHeader("state", state.value.toByteArray()))

        return record
    }
}

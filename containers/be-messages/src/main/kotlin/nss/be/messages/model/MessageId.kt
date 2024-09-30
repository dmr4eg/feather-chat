package nss.be.messages.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Embeddable
import java.io.Serializable

/**
 * Message ID object
 * @param chatUUID ID of the chat
 * @param senderUUID ID of the user
 * @param timestamp Timestamp of the message
 */
@Embeddable
data class MessageId(
    @Schema(example = "665c599d-5c8d-4d20-aaab-7ffaba150606", required = true, description = "ID of the chat")
    @get:JsonProperty("chatUUID", required = true)
    val chatUUID: java.util.UUID,

    @Schema(example = "665c599d-5c8d-4d20-aaab-7ffaba150606", required = true, description = "ID of the user")
    @get:JsonProperty("senderUUID", required = true)
    val senderUUID: java.util.UUID,

    @Schema(example = "1627846382000", required = true, description = "Timestamp of the message")
    @get:JsonProperty("timestamp", required = true)
    var timestamp: kotlin.Long
) : Serializable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is MessageId) return false
        return chatUUID == other.chatUUID && senderUUID == other.senderUUID && timestamp == other.timestamp
    }

    override fun hashCode(): Int {
        return chatUUID.hashCode() + senderUUID.hashCode() + timestamp.hashCode()
    }
}



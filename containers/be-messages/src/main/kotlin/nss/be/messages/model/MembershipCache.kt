package nss.be.messages.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.util.*
import javax.validation.constraints.NotNull

/**
 * user membership cache object
 * @param chatUUID ID of the chat
 * @param userUUID ID of the user
 * @param ttl Time To Live
 */
@Entity
@Table(name = "membership_cache")
data class MembershipCache(
    @Id
    @NotNull
    @GeneratedValue(generator = "uuid2")
    val id: UUID? = null,

    @NotNull
    @Schema(example = "665c599d-5c8d-4d20-aaab-7ffaba150606", required = true, description = "ID of the chat")
    @get:JsonProperty("chatUUID", required = true)
    val chatUUID: java.util.UUID,

    @NotNull
    @Schema(example = "665c599d-5c8d-4d20-aaab-7ffaba150606", required = true, description = "ID of the user")
    @get:JsonProperty("userUUID", required = true)
    val userUUID: java.util.UUID,

    @NotNull
    @Schema(example = "1627846382000", required = true, description = "Time To Live")
    @get:JsonProperty("ttl", required = true)
    val ttl: kotlin.Long
) {
    constructor(userUUID: UUID, chatUUID: UUID) : this(
        // TODO make ttl = 12 hours during constructor
        UUID.randomUUID(), userUUID, chatUUID, 0L
    )
}

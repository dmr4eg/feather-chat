package nss.be.messages.model

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.v3.oas.annotations.media.Schema

/**
 * Error object
 * @param message Error message
 */
data class Error(

    @Schema(example = "Error message", description = "Error message")
    @get:JsonProperty("message") val message: kotlin.String? = null
) {

}


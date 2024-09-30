package nss.be.messages.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.Parameter
import io.swagger.v3.oas.annotations.media.ArraySchema
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.security.SecurityRequirement
import nss.be.messages.model.Error
import nss.be.messages.model.Message
import nss.be.messages.model.MessageId
import nss.be.messages.service.MembershipService
import nss.be.messages.service.MessageService
import nss.be.messages.service.impl.MembershipServiceImpl
import nss.be.messages.service.impl.MessageServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid
import javax.validation.constraints.NotNull

@RestController
@Validated
@RequestMapping("\${api.base-path:}/api")
class MessagesApiController(
    @Autowired
    private val msgService: MessageService,
    @Autowired
    private val membershipService: MembershipService
) {

    @Operation(
        summary = "Delete message",
        operationId = "messagesDelete",
        description = """if authUserUUID in chat""",
        responses = [
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad Request",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(responseCode = "200", description = "OK")],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @RequestMapping(
        method = [RequestMethod.DELETE],
        value = ["/messages"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun messagesDelete(
        @NotNull @Parameter(
            description = "UUID of the user",
            required = true
        ) @Valid @RequestParam(value = "authUserUUID", required = true)
        authUserUUID: java.util.UUID,

        @Parameter(description = "", required = true) @Valid @RequestBody
        messageId: MessageId
    ): ResponseEntity<Unit> {
        return try {
            membershipService.authorize(authUserUUID, messageId.chatUUID, messageId.senderUUID)
            val message = msgService.getMessageById(messageId)
            return if (message != null && message.id.chatUUID == authUserUUID) {
                msgService.deleteMessageById(messageId)
                ResponseEntity.noContent().build()
            } else if (message == null) {
                ResponseEntity.notFound().build()
            } else {
                ResponseEntity.status(HttpStatus.FORBIDDEN).build()
            }
        } catch (e: Exception) {
//            TODO handle
            ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }

    }

    @Operation(
        summary = "Get messages",
        operationId = "messagesGet",
        description = """if authUserUUID in chat""",
        responses = [
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad Request",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "200",
                description = "OK",
                content = [Content(array = ArraySchema(schema = Schema(implementation = Message::class)))]
            )],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @RequestMapping(
        method = [RequestMethod.GET],
        value = ["/messages"],
        produces = ["application/json"]
    )
    fun messagesGet(
        @NotNull @Parameter(
            description = "UUID of the user",
            required = true
        ) @Valid @RequestParam(value = "authUserUUID", required = true)
        authUserUUID: java.util.UUID,

        @NotNull @Parameter(
            description = "UUID of the chat",
            required = true
        ) @Valid @RequestParam(value = "chatUUID", required = true)
        chatUUID: java.util.UUID,

        @NotNull @Parameter(
            description = "Limit of the list",
            required = true
        ) @Valid @RequestParam(value = "limit", required = true)
        limit: kotlin.Int,

        @NotNull @Parameter(
            description = "Offset of the list",
            required = true
        ) @Valid @RequestParam(value = "offset", required = true)
        offset: kotlin.Int

    ): ResponseEntity<List<Message>> {
        return try {
            membershipService.authorize(authUserUUID, chatUUID)

            val messages: List<Message> = msgService.getMessages(chatUUID, limit, offset)

            return if (messages.isNotEmpty()) {
                ResponseEntity.ok(messages)
            } else {
                ResponseEntity.notFound().build()
            }
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(
        summary = "Send message",
        operationId = "messagesPost",
        description = """if authUserUUID in chat""",
        responses = [
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad Request",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(responseCode = "200", description = "OK")],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @RequestMapping(
        method = [RequestMethod.POST],
        value = ["/messages"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun messagesPost(
        @NotNull @Parameter(
            description = "UUID of the user",
            required = true
        ) @Valid @RequestParam(value = "authUserUUID", required = true)
        authUserUUID: java.util.UUID,

        @Parameter(description = "", required = true) @Valid @RequestBody
        message: Message
    ): ResponseEntity<Unit> {
        return try {
            membershipService.authorize(authUserUUID, message.id.chatUUID, message.id.senderUUID)

            if (msgService.getMessageById(message.id) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT).build()
            }
            msgService.sendMessage(message)
            ResponseEntity.status(HttpStatus.CREATED).build()

        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        } catch (e: Exception) {
            ResponseEntity.badRequest().build()
        }
    }

    @Operation(
        summary = "Edit message",
        operationId = "messagesPut",
        description = """if authUserUUID in chat""",
        responses = [
            ApiResponse(
                responseCode = "401",
                description = "Unauthorized access",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(
                responseCode = "400",
                description = "Bad Request",
                content = [Content(schema = Schema(implementation = Error::class))]
            ),
            ApiResponse(responseCode = "200", description = "OK")],
        security = [SecurityRequirement(name = "bearerAuth")]
    )
    @RequestMapping(
        method = [RequestMethod.PUT],
        value = ["/messages"],
        produces = ["application/json"],
        consumes = ["application/json"]
    )
    fun messagesPut(
        @NotNull @Parameter(
            description = "UUID of the user",
            required = true
        ) @Valid @RequestParam(value = "authUserUUID", required = true)
        authUserUUID: java.util.UUID,

        @Parameter(description = "", required = true) @Valid @RequestBody
        message: Message
    ): ResponseEntity<Unit> {
        return try {
            membershipService.authorize(authUserUUID, message.id.chatUUID, message.id.senderUUID)

            val existingMessage: Message? = msgService.getMessageById(message.id)
            return if (existingMessage == null || existingMessage.id.chatUUID != authUserUUID) {
                ResponseEntity.notFound().build()
            } else {
                msgService.editMessage(message)
                ResponseEntity.ok().build()
            }
        } catch (e: Exception) {
//            TODO handle
            ResponseEntity.status(HttpStatus.FORBIDDEN).build()
        }
    }
}

package nss.be.messages.repository

import nss.be.messages.model.Message
import nss.be.messages.model.MessageId
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
@Transactional(readOnly = true)
interface MessageReadOnlyRepository : JpaRepository<Message, MessageId> {
    fun findById_ChatUUID(ChatUUID: UUID, pageable: PageRequest): List<Message>
}

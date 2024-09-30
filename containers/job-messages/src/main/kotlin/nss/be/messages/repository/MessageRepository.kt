package nss.be.messages.repository;
import nss.be.messages.model.Message
import nss.be.messages.model.MessageId
import org.springframework.data.domain.PageRequest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*


@Repository
interface MessageRepository : JpaRepository<Message, MessageId> {
}

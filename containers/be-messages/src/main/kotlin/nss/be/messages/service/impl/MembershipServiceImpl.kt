package nss.be.messages.service.impl

import nss.be.messages.repository.MembershipCacheRepository
import nss.be.messages.model.MembershipCache
import nss.be.messages.service.MembershipService
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class MembershipServiceImpl(
    private val membershipCacheRepository: MembershipCacheRepository
) : MembershipService {
    private val logger = LoggerFactory.getLogger(MessageServiceImpl::class.java)

    override fun authorize(authUserUUID: UUID, chatUUID: UUID, userUUID: UUID) {
        if (authUserUUID != userUUID) {
            logger.error("Unauthorized: User does not have permission to view these messages")
            throw IllegalArgumentException("Unauthorized: User does not have permission to view these messages")
        }
        authorize(authUserUUID, chatUUID)
        logger.info("User $authUserUUID is authorized to view messages in chat $chatUUID")
    }

    override fun authorize(authUserUUID: UUID, chatUUID: UUID) {
        val membershipCache = membershipCacheRepository.findMembershipCacheByUserUUID(authUserUUID)

        if (membershipCache == null || !isAuthorized(membershipCache, chatUUID)) {
            logger.error("Unauthorized: User does not have permission to view these messages")
            throw IllegalArgumentException("Unauthorized: User does not have permission to view these messages")
        }
        logger.info("User $authUserUUID is authorized to view messages in chat $chatUUID")
    }

    private fun isAuthorized(membershipCache: MembershipCache, chatUUID: UUID): Boolean {
        //TODO
        return true
    }

    override fun saveMembershipToCache(membershipCache: MembershipCache) {
        membershipCacheRepository.saveMembershipCache(membershipCache)
        logger.info("Saved membership cache for user ${membershipCache.userUUID}")
    }

    override fun deleteMembershipFromCache(userUUID: UUID) {
        membershipCacheRepository.deleteMembershipCache(userUUID)
        logger.info("Deleted membership cache for user $userUUID")
    }
}

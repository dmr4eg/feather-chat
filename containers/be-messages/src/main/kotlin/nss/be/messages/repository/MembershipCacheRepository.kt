package nss.be.messages.repository

import nss.be.messages.model.MembershipCache
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class MembershipCacheRepository(
    private val redisTemplate: RedisTemplate<String, Any>
) {
    private val keyPrefix = "membership:"

    fun saveMembershipCache(membershipCache: MembershipCache) {
        val key = "$keyPrefix${membershipCache.userUUID}"
        redisTemplate.opsForValue().set(key, membershipCache)
    }

    fun findMembershipCacheByUserUUID(userUUID: UUID): MembershipCache? {
        val key = "$keyPrefix$userUUID"
        return redisTemplate.opsForValue().get(key) as? MembershipCache
    }

    fun deleteMembershipCache(userUUID: UUID) {
        val key = "$keyPrefix$userUUID"
        redisTemplate.delete(key)
    }
}

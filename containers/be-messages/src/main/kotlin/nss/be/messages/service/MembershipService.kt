package nss.be.messages.service

import nss.be.messages.model.MembershipCache
import java.util.*

/**
 * Service interface for managing and authorizing membership caches.
 * This interface defines methods for authorizing users, saving and deleting membership data from the cache.
 */
interface MembershipService {
    /**
     * Authorizes the user to access the messages in a chat.
     * The method checks if the authenticated user is the same as the target user, and if so, authorizes the user.
     *
     * @param authUserUUID the unique identifier of the authenticated user
     * @param chatUUID the unique identifier of the chat
     * @param userUUID the unique identifier of the target user
     * @throws IllegalArgumentException if the authenticated user is not the target user or is not authorized
     */
    fun authorize(authUserUUID: UUID, chatUUID: UUID, userUUID: UUID)

    /**
     * Authorizes the user to access the messages in a chat based on their membership cache.
     *
     * @param authUserUUID the unique identifier of the authenticated user
     * @param chatUUID the unique identifier of the chat
     * @throws IllegalArgumentException if the user is not authorized to access the chat
     */
    fun authorize(authUserUUID: UUID, chatUUID: UUID)

    /**
     * Saves the user's membership information to the cache.
     *
     * @param membershipCache the [MembershipCache] object containing membership information to be saved
     */
    fun saveMembershipToCache(membershipCache: MembershipCache)

    /**
     * Deletes the user's membership information from the cache.
     *
     * @param userUUID the unique identifier of the user whose membership data should be deleted
     */
    fun deleteMembershipFromCache(userUUID: UUID)
}
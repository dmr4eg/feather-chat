package nss.be.messages.service

import nss.be.messages.model.Message

/**
 * Service interface for handling message operations.
 * This interface defines methods for saving, marking messages as read/received, and editing messages.
 */
interface MessageService {
    /**
     * Saves a new message to the repository.
     *
     * @param message the message to be saved
     */
    fun save(message: Message)

    /**
     * Marks a message as read.
     *
     * @param message the message to be marked as read
     */
    fun makeRead(message: Message)

    /**
     * Marks a message as received.
     *
     * @param message the message to be marked as received
     */
    fun makeReceived(message: Message)

    /**
     * Edits an existing message, updating its text and state.
     *
     * @param message the message to be edited
     */
    fun edit(message: Message)
}
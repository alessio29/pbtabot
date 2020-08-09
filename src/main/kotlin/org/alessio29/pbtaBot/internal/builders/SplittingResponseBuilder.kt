package org.alessio29.pbtaBot.internal.builders

import org.alessio29.pbtaBot.internal.builders.MessageSplitter.splitMessage
import java.util.*

abstract class SplittingResponseBuilder(private val messageLengthLimit: Int) : ResponseBuilder() {

    fun sendResponse() {
        val privatePart = privatePart.toString()
        val publicPart = publicPart.toString()
        if (publicPart.isNotEmpty() && hasCommandResult) {
            splitAndSendToOrigin(publicPart)
        }
        if (privatePart.isNotEmpty()) {
            splitAndSendPrivate(privatePart)
        }
    }

    override fun reportError(id: UUID?, word: String?, e: Exception) {
        splitAndSendToOrigin(
            """
                Error while executing command $word. Details: ${e.message}
                [$id]
                """.trimIndent()
        )
    }

    private fun splitAndSendToOrigin(message: String) {
        val asMention: String? = userMention
        val reservedHeaderLength = (asMention?.length ?: 0) + RESERVED_SUFFIX_LENGTH
        val messageParts: List<String> =
            splitMessage(message, messageLengthLimit - reservedHeaderLength)
        val header: String
        header = if (message.contains("\n") || messageParts.size > 1) {
            asMention + ReplyBuilder.NEWLINE
        } else {
            asMention + ReplyBuilder.SPACE
        }
        for (part in messageParts) {
            sendReplyToOrigin(header + part)
        }
    }

    private fun splitAndSendPrivate(message: String) {
        val messageParts: List<String> = splitMessage(message, messageLengthLimit)
        for (part in messageParts) {
            sendPrivateReply(part)
        }
    }

    companion object {
        private val RESERVED_SUFFIX_LENGTH: Int =
            ReplyBuilder.SPACE.length.coerceAtLeast(ReplyBuilder.NEWLINE.length)
    }

}

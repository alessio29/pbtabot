package org.alessio29.pbtaBot.internal.builders

import org.alessio29.pbtaBot.internal.commands.CommandExecutionResult
import java.util.*

open abstract class ResponseBuilder {

    protected var hasCommandResult = false
    protected val publicPart = StringBuilder()
    protected val privatePart = StringBuilder()

    fun addRaw(string: String) {
        publicPart.append(string)
        if (shouldAppendWhitespace(string)) {
            publicPart.append(' ')
        }
    }

    fun addResult(result: CommandExecutionResult) {
        val resultString: String = result.result
        val toAppend =
            if (shouldAppendWhitespace(resultString)) "$resultString " else resultString
        if (result.isPrivateMessage) {
            privatePart.append(toAppend)
        } else {
            hasCommandResult = true
            publicPart.append(toAppend)
        }
    }

    abstract fun reportError(id: UUID?, word: String?, e: Exception)
    abstract val userMention: String?

    protected abstract fun sendReplyToOrigin(message: String)
    protected abstract fun sendPrivateReply(message: String)

    companion object {
        private fun shouldAppendWhitespace(string: String): Boolean {
            if (string.isEmpty()) return false
            val last = string[string.length - 1]
            return !Character.isWhitespace(last)
        }
    }
}

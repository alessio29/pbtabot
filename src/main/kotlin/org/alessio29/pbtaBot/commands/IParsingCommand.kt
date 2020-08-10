package org.alessio29.pbtaBot.commands

import org.alessio29.pbtaBot.internal.messages.IMessageContext

interface IParsingCommand {
    fun parseAndExecuteOrNull(message: IMessageContext, command: String): CommandExecutionResult
}
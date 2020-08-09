package org.alessio29.pbtaBot.internal.commands.interfaces

import org.alessio29.pbtaBot.internal.commands.CommandExecutionResult
import org.alessio29.pbtaBot.internal.messages.IMessageReceived

interface IParsingCommand {
    fun parseAndExecuteOrNull(message: IMessageReceived<*>, command: String): CommandExecutionResult
}
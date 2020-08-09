package org.alessio29.pbtaBot.apiActions

import org.alessio29.pbtaBot.internal.commands.CommandExecutionResult
import org.alessio29.pbtaBot.internal.messages.IMessageReceived

interface IBotAction {
    fun doAction(message: IMessageReceived<*>, args: Array<String?>?): CommandExecutionResult
}
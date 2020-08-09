package org.alessio29.pbtaBot.apiActions

import org.alessio29.pbtaBot.internal.builders.ReplyBuilder
import org.alessio29.pbtaBot.internal.commands.CommandExecutionResult
import org.alessio29.pbtaBot.internal.messages.IMessageReceived

class PingAction : IBotAction {

    override fun doAction(message: IMessageReceived<*>, args: Array<String?>?): CommandExecutionResult {
        return CommandExecutionResult(
            ReplyBuilder().attach("Hey, ").attach(message.getAuthorMention()).attach(" PbtaBot is ready!").newLine()
                .toString(),
            1
        )
    }
}

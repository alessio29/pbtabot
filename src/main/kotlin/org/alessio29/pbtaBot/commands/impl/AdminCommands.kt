package org.alessio29.pbtaBot.commands.impl

import org.alessio29.pbtaBot.commands.CommandCategory
import org.alessio29.pbtaBot.commands.CommandExecutionResult
import org.alessio29.pbtaBot.commands.SimpleCommand
import org.alessio29.pbtaBot.internal.builders.ReplyBuilder
import org.alessio29.pbtaBot.internal.builders.buildReply
import org.alessio29.pbtaBot.internal.messages.IMessageContext

object PingCommand :
    SimpleCommand("ping", CommandCategory.ADMIN, "Checks PbtaBot readiness") {

    override fun execute(message: IMessageContext, args: List<String>) =
        CommandExecutionResult(
            buildReply {
                attach("Hey, ${message.authorMention}, PbtaBot is ready!").newLine()
            }
        )
}

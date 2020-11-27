package org.alessio29.pbtaBot.commands.impl

import org.alessio29.pbtaBot.capabilities.IGetNumberOfServersCapability
import org.alessio29.pbtaBot.commands.CommandCategory
import org.alessio29.pbtaBot.commands.CommandExecutionResult
import org.alessio29.pbtaBot.commands.SimpleCommand
import org.alessio29.pbtaBot.internal.builders.buildReply
import org.alessio29.pbtaBot.internal.messages.IMessageContext

object PingCommand :
    SimpleCommand("ping", CommandCategory.ADMIN, "Checks PbtaBot readiness") {

    override fun execute(message: IMessageContext, args: List<String>) =
        CommandExecutionResult(
            buildReply {
                attach("Hey, ${message.authorMention}, PbtaBot is ready!").newLine()
            },
            1
        )
}

object InfoCommand :
    SimpleCommand("info", CommandCategory.ADMIN, "Returns bot stats") {

    override fun execute(message: IMessageContext, args: List<String>): CommandExecutionResult {

        val msg = message.getPlatformCapability(IGetNumberOfServersCapability::class.java)
            ?.run { getNumberOfServers().toString() }
            ?: "Sorry, unable to get info!"

        return CommandExecutionResult(
            buildReply {
                attach(
                    "Bot registered at $msg server(s)"
                ).newLine()
            },
            1
        )
    }
}
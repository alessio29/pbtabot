package org.alessio29.pbtaBot.commands

import org.alessio29.pbtaBot.internal.messages.IMessageContext

interface ICommand {
    val name: String
    val category: CommandCategory
    val description: String
    val aliases: List<String>
    val arguments: List<String>

    fun execute(message: IMessageContext, args: List<String>): CommandExecutionResult
}


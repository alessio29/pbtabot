package org.alessio29.pbtaBot.internal.commands.interfaces

import org.alessio29.pbtaBot.internal.commands.CommandCategory
import org.alessio29.pbtaBot.internal.commands.CommandExecutionResult
import org.alessio29.pbtaBot.internal.messages.IMessageReceived

interface ICommand<T> {
    val name: String
    val category: CommandCategory
    val description: String
    val aliases: Array<String>
    val arguments: Array<String>

    @Throws(Exception::class)
    fun execute(message: IMessageReceived<*>, args: Array<String>?): CommandExecutionResult
}

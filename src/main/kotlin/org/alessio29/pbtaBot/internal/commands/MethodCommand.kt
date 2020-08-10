package org.alessio29.pbtaBot.internal.commands

import org.alessio29.pbtaBot.internal.commands.interfaces.ICommand
import org.alessio29.pbtaBot.internal.messages.IMessageReceived
import java.lang.reflect.Method

class MethodCommand(
    override val name: String,
    override val category: CommandCategory,
    override val description: String,
    override val aliases: Array<String>,
    override val arguments: Array<String>,
    private var methodOwner : Any?,
    private var method: Method

) : ICommand<Any> {

    @Throws(Exception::class)
    override fun execute(message: IMessageReceived<*>, args: Array<String>?): CommandExecutionResult {
        return (method!!.invoke(methodOwner, message, args) as CommandExecutionResult)
    }
}
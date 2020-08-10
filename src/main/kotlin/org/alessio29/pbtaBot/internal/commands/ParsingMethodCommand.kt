package org.alessio29.pbtaBot.internal.commands

import org.alessio29.pbtaBot.internal.commands.interfaces.IParsingCommand
import org.alessio29.pbtaBot.internal.messages.IMessageReceived
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Method

class ParsingMethodCommand(private val methodOwner: Any?, private val method: Method) :
    IParsingCommand {

    override fun parseAndExecuteOrNull(message: IMessageReceived<*>, command: String): CommandExecutionResult {
        return try {
            method.invoke(methodOwner, message, command) as CommandExecutionResult
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        } catch (e: InvocationTargetException) {
            throw RuntimeException(e)
        }
    }

    override fun toString(): String {
        return javaClass.simpleName +
                "{ method: " + method.declaringClass.name + "::" + method.name +
                "; methodOwner: " + methodOwner +
                "}"
    }

}
package org.alessio29.pbtaBot.internal.commands

import org.alessio29.pbtaBot.commands.ICommand
import org.alessio29.pbtaBot.commands.IParsingCommand
import java.util.*

object CommandRegistry {

    private val registeredCommands: MutableMap<String, ICommand> = HashMap()
    private val parsingCommands: MutableList<IParsingCommand> = ArrayList()

    fun reset() {
        registeredCommands.clear()
        parsingCommands.clear()
    }

    fun registerCommand(newCommand: ICommand) {
        if (newCommand.aliases.isEmpty()) {
            for (alias in newCommand.aliases) {
                registeredCommands[alias] = newCommand
            }
        }
        if (newCommand is IParsingCommand) {
            registerParsingCommand(newCommand)
        }
        registeredCommands[newCommand.name] = newCommand
    }

    private fun registerParsingCommand(newCommand: IParsingCommand) {
        parsingCommands.add(newCommand)
    }

    fun getRegisteredCommands() = registeredCommands.values

    fun getCommandByName(command: String) = registeredCommands[command]

    fun getRegisteredParsingCommands() = parsingCommands

}
package org.alessio29.pbtaBot.internal.commands

import org.alessio29.pbtaBot.internal.commands.interfaces.annotations.CommandCallback
import org.alessio29.pbtaBot.internal.commands.interfaces.annotations.CommandCategoryOwner
import org.alessio29.pbtaBot.internal.commands.interfaces.annotations.ParsingCommandCallback
import org.alessio29.pbtaBot.internal.commands.interfaces.ICommand
import org.alessio29.pbtaBot.internal.commands.interfaces.IParsingCommand
import java.lang.reflect.Modifier
import java.util.*

class CommandRegistry private constructor() {

    companion object {
        private var INSTANCE: CommandRegistry? = null

        fun getInstance(): CommandRegistry =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: CommandRegistry()
            }
    }

    private val registeredCommands: MutableMap<String, ICommand<*>> = HashMap();
    private val parsingCommands: MutableList<IParsingCommand> = ArrayList();

    fun reset() {
        registeredCommands.clear();
        parsingCommands.clear();
    }

    fun registerCommand(newCommand: ICommand<*>) {

        if (newCommand.aliases.isEmpty()) {
            for (alias in newCommand.aliases) {
                registeredCommands.put(alias, newCommand);
            }
        }
        if (newCommand is IParsingCommand) {
            registerParsingCommand(newCommand);
        }
        registeredCommands.put(newCommand.name, newCommand);
    }

    private fun registerParsingCommand(newCommand: IParsingCommand) {
        parsingCommands.add(newCommand);
    }

    fun registerCommandsFromStaticMethods(methodClass: Class<*>) {
        registerCommandsFromMethods(null, methodClass);
    }

    private fun registerCommandsFromMethods(methodOwner: Object?, methodClass: Class<*>) {

        val shouldBeStatic = methodOwner == null;

        val commandCategoryAnn: CommandCategoryOwner = methodClass.getDeclaredAnnotation(CommandCategoryOwner::class.java)
        val classCommandCategory: CommandCategory = commandCategoryAnn.value

        for (method in methodClass.declaredMethods) {
//            if (shouldBeStatic != Modifier.isStatic(method.getModifiers()))
//                continue
            val commandAnn: CommandCallback = method.getDeclaredAnnotation(CommandCallback::class.java)
            if (commandAnn != null) {
                registerCommand(
                    MethodCommand(
                        commandAnn.name,
                        classCommandCategory ?: commandAnn.category,
                        commandAnn.description,
                        commandAnn.aliases,
                        commandAnn.arguments,
                        methodOwner,
                        method
                    )
                );
            }

            val parsingCommandAnn: ParsingCommandCallback = method.getDeclaredAnnotation(ParsingCommandCallback::class.java)

            if (parsingCommandAnn != null) {
                registerParsingCommand(ParsingMethodCommand (methodOwner, method));
            }
        }


    }

    fun getRegisteredCommands() : Collection<ICommand<*>> {
        return registeredCommands.values;
    }


    fun getCommandByName(command: String): ICommand<*>? {
        return registeredCommands.get(command);
    }

    fun getRegisteredParsingCommands():  Collection<IParsingCommand> {
        return parsingCommands;
    }

}
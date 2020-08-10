package org.alessio29.pbtaBot.internal.commands


import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.alessio29.pbtaBot.internal.builders.ReplyBuilder
import org.alessio29.pbtaBot.internal.builders.ResponseBuilder
import org.alessio29.pbtaBot.internal.commands.interfaces.ICommand
import org.alessio29.pbtaBot.internal.messages.IMessageReceived
import org.apache.commons.text.StringEscapeUtils
import java.util.*

class CommandInterpreter {
    private val registry = CommandRegistry

    private val defaultPrefix = "~"

    fun run(message: IMessageReceived<MessageReceivedEvent>, responseBuilder: ResponseBuilder) {
        val prefix: String =  defaultPrefix// TODO Prefixes.getPrefix(message.getAuthorId())
        val rawMessage: String = message.getRawMessage()
        val strippedMessage: String = ReplyBuilder.removeBlocks(ReplyBuilder.removeQuotes(rawMessage))
        val words = strippedMessage.split("\\s+").toTypedArray()
        for (i in words.indices) {
            words[i] = StringEscapeUtils.unescapeJava(words[i])
        }
        var index = 0
        while (index < words.size) {
            val word = words[index]
            var isCommand = false
            if (word.trim { it <= ' ' }.startsWith(prefix)) {
                val command = word.replaceFirst(prefix.toRegex(), "")
                val cmd: ICommand<*>? = registry.getCommandByName(command)
                if (cmd != null) {
                    isCommand = true
                    val args =
                        Arrays.copyOfRange(words, index + 1, words.size)
                    try {
                        val res: CommandExecutionResult = cmd.execute(message, args)
                        responseBuilder.addResult(res)
                        index += res.toSkip
                    } catch (e: Exception) {
                        val errorId = UUID.randomUUID()
                        println(
                            "Exception Id: $errorId\nException while executing command: "
                        )
                        responseBuilder.reportError(errorId, word, e)
                        index++
                    }
                } else {
                    for (pcmd in registry.getRegisteredParsingCommands()) {
                        try {
                            val res: CommandExecutionResult = pcmd.parseAndExecuteOrNull(message, command)
                            if (res != null) {
                                isCommand = true
                                index++
                                responseBuilder.addResult(res)
                                break
                            }
                        } catch (e: Exception) {
                            val errorId = UUID.randomUUID()
                            println("Exception while executing command: ")
                            responseBuilder.reportError(errorId, word, e)
                            index++
                        }
                    }
                }
            }
            if (!isCommand) {
                responseBuilder.addRaw(word)
                index++
            }
        }
    }


}

package org.alessio29.pbtaBot.internal

import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User
import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import net.dv8tion.jda.api.hooks.ListenerAdapter
import org.alessio29.pbtaBot.internal.builders.DiscordResponseBuilder
import org.alessio29.pbtaBot.internal.commands.CommandInterpreter
import org.alessio29.pbtaBot.internal.messages.DiscordMessageContext

class ParsingInputListener : ListenerAdapter() {

    override fun onMessageReceived(event: MessageReceivedEvent) {
        val author: User = event.author
        val channel: MessageChannel = event.channel
        if (author.isBot) {
            // do not respond to bots (including myself :) )
            return
        }

        val responseBuilder = DiscordResponseBuilder(author, channel)
        CommandInterpreter().run(DiscordMessageContext(event), responseBuilder)
        responseBuilder.sendResponse()
    }
}

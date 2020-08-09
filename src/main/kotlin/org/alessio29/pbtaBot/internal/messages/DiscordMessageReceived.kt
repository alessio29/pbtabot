package org.alessio29.pbtaBot.internal.messages

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class DiscordMessageReceived(private val event: MessageReceivedEvent) : IMessageReceived<MessageReceivedEvent> {

    private val guildId: String = event.guild.id
    private val channelId: String = event.channel.id
    private val authorId: String = event.author.id
    private val authorMention: String = event.author.asMention
    private val rawMessage: String = event.message.contentRaw

    override fun getGuildId(): String {
        return guildId
    }

    override fun getChannelId(): String {
        return channelId
    }

    override fun getAuthorId(): String {
        return authorId
    }


    override fun getAuthorMention(): String {
        return authorMention
    }

    override fun getRawMessage(): String {
        return rawMessage
    }

    override fun getOriginalEvent(): MessageReceivedEvent {
        return event
    }

}



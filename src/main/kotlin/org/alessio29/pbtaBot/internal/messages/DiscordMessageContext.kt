package org.alessio29.pbtaBot.internal.messages

import net.dv8tion.jda.api.events.message.MessageReceivedEvent

class DiscordMessageContext(override val originalEvent: MessageReceivedEvent) : IMessageContext {

    override val guildId: String = originalEvent.guild.id
    override val channelId: String = originalEvent.channel.id
    override val authorId: String = originalEvent.author.id
    override val authorMention: String = originalEvent.author.asMention
    override val rawMessage: String = originalEvent.message.contentRaw

    override fun <T> getPlatformCapability(capability: Class<T>): T? {
        // TODO provide Discord-specific platform capabilities
        return null
    }
}



package org.alessio29.pbtaBot.internal.discord

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.alessio29.pbtaBot.capabilities.IGetNumberOfServersCapability
import org.alessio29.pbtaBot.internal.messages.IMessageContext

class DiscordMessageContext(override val originalEvent: MessageReceivedEvent) : IMessageContext {

    override val guildId: String = originalEvent.guild.id
    override val channelId: String = originalEvent.channel.id
    override val authorId: String = originalEvent.author.id
    override val authorMention: String = originalEvent.author.asMention
    override val rawMessage: String = originalEvent.message.contentRaw

    @Suppress("UNCHECKED_CAST")
    override fun <T : Any> getPlatformCapability(capability: Class<T>): T? =
        when (capability) {
            IGetNumberOfServersCapability::class.java -> DiscordGetNumberOfServersCapability(originalEvent.jda)
            else -> null
        } as T?
}



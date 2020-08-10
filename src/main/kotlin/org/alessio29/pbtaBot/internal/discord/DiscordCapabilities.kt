package org.alessio29.pbtaBot.internal.discord

import net.dv8tion.jda.api.events.message.MessageReceivedEvent
import org.alessio29.pbtaBot.capabilities.IGetNumberOfServersCapability

class DiscordCapabilities(originalMessage: MessageReceivedEvent) :
    IGetNumberOfServersCapability
{
    private val jda = originalMessage.jda

    override fun getNumberOfServers(): Int = jda.guilds.size
}
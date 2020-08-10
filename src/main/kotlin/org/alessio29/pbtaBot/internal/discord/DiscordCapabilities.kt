package org.alessio29.pbtaBot.internal.discord

import net.dv8tion.jda.api.JDA
import org.alessio29.pbtaBot.capabilities.IGetNumberOfServersCapability

class DiscordGetNumberOfServersCapability(private val jda: JDA) : IGetNumberOfServersCapability {
    override fun getNumberOfServers(): Int = jda.guilds.size
}
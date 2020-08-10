package org.alessio29.pbtaBot.internal.messages

interface IMessageContext {
    val guildId: String
    val channelId: String
    val authorId: String
    val authorMention: String
    val rawMessage: String

    val originalEvent: Any

    fun <T : Any> getPlatformCapability(capability: Class<T>): T?
}

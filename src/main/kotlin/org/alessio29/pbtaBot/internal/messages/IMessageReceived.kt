package org.alessio29.pbtaBot.internal.messages

interface IMessageReceived<T> {

    fun getGuildId(): String

    fun getChannelId(): String

    fun getAuthorId(): String

    fun getAuthorMention(): String

    fun getRawMessage(): String

    fun getOriginalEvent(): T
}

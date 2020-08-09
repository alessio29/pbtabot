package org.alessio29.pbtaBot.internal.builders

import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

class DiscordResponseBuilder(user: User, channel: MessageChannel) :  SplittingResponseBuilder(MESSAGE_LENGTH_LIMIT) {

    private val user: User = user
    private val channel: MessageChannel = channel

    override fun sendReplyToOrigin(message: String) {
        channel.sendMessage(message).queue()
    }

    override fun sendPrivateReply(message: String) {
        user.openPrivateChannel().queue({ channel -> channel.sendMessage(message).queue() })
    }

    override val userMention: String
        get() = user.asMention

    companion object {
        const val MESSAGE_LENGTH_LIMIT = 2000
    }

}
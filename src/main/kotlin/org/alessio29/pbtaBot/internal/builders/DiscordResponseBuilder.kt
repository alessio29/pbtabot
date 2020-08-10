package org.alessio29.pbtaBot.internal.builders

import net.dv8tion.jda.api.entities.MessageChannel
import net.dv8tion.jda.api.entities.User

class DiscordResponseBuilder(
    private val user: User,
    private val channel: MessageChannel
) : SplittingResponseBuilder(MESSAGE_LENGTH_LIMIT) {

    override fun sendReplyToOrigin(message: String) {
        channel.sendMessage(message).queue()
    }

    override fun sendPrivateReply(message: String) {
        user.openPrivateChannel().queue { channel -> channel.sendMessage(message).queue() }
    }

    override val userMention: String
        get() = user.asMention

    companion object {
        const val MESSAGE_LENGTH_LIMIT = 2000
    }

}
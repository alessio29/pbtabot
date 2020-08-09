package org.alessio29.pbtaBot.commands

import org.alessio29.pbtaBot.apiActions.PingAction
import org.alessio29.pbtaBot.internal.commands.CommandCategory
import org.alessio29.pbtaBot.internal.commands.CommandExecutionResult
import org.alessio29.pbtaBot.internal.commands.interfaces.annotations.CommandCallback
import org.alessio29.pbtaBot.internal.commands.interfaces.annotations.CommandCategoryOwner
import org.alessio29.pbtaBot.internal.messages.IMessageReceived

@CommandCategoryOwner(CommandCategory.ADMIN)
object AdminCommands  {

    @CommandCallback(name = "ping", description = "Checks PbtaBot readiness", aliases = [], arguments = [])
    fun ping(message: IMessageReceived<*>, args: Array<String?>?): CommandExecutionResult {
        return PingAction().doAction(message, args)
    }
}

package org.alessio29.pbtaBot.internal.commands

import org.alessio29.pbtaBot.commands.AdminCommands

object Commands {
    fun registerDefaultCommands() {
        val registry = CommandRegistry.getInstance()

        // admin commands
        registry.registerCommandsFromStaticMethods(AdminCommands::class.java)

    }
}
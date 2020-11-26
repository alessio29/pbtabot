package org.alessio29.pbtaBot.commands

import org.alessio29.pbtaBot.internal.commands.CommandRegistry

@Suppress("LeakingThis")
abstract class SimpleCommand    (
    override val name: String,
    override val category: CommandCategory,
    override val description: String,
    override val aliases: List<String> = emptyList(),
    override val arguments: List<String> = emptyList()
): ICommand {
    init {
        CommandRegistry.registerCommand(this)
    }
}
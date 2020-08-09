package org.alessio29.pbtaBot

import net.dv8tion.jda.api.JDABuilder
import org.alessio29.pbtaBot.internal.ParsingInputListener
import org.alessio29.pbtaBot.internal.commands.Commands
import javax.security.auth.login.LoginException
import kotlin.system.exitProcess

fun main(args: Array<String>) {

    Commands.registerDefaultCommands()
    connect(args[0]);
    println("Connected!")
}

private fun connect(token: String) {
    try {

        JDABuilder.createDefault(token).addEventListeners(ParsingInputListener()).build()
    } catch (ex: LoginException) {
        System.err.println(ex.message)
        exitProcess(1);
    }
}
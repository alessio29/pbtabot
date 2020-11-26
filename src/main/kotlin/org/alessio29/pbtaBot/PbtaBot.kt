package org.alessio29.pbtaBot

import net.dv8tion.jda.api.JDABuilder
import org.alessio29.pbtaBot.commands.impl.InfoCommand
import org.alessio29.pbtaBot.commands.impl.PingCommand
import org.alessio29.pbtaBot.internal.ParsingInputListener
import javax.security.auth.login.LoginException
import kotlin.system.exitProcess

const val TOKEN = 0
const val PASSWORD = 1
const val REDIS_HOST = 2
const val REDIS_PORT = 3
const val REDIS_PASSW = 4

fun main(args: Array<String>) {

    val cmd1  = PingCommand
    val cmd2 = InfoCommand

    val token = args[TOKEN]
    connect(token)
    println("Connected!")
}

private fun connect(token: String) {
    try {
        JDABuilder.createDefault(token).addEventListeners(ParsingInputListener()).build()
    } catch (ex: LoginException) {
        System.err.println(ex.message)
        exitProcess(1)
    }
}
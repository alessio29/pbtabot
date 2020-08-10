package org.alessio29.pbtaBot.commands

class CommandExecutionResult(
    val result: String = "",
    val toSkip: Int = 0,
    val isPrivateMessage: Boolean = false
) {
    override fun toString(): String =
        "CommandExecutionResult{result='$result', toSkip=$toSkip, privateMessage=$isPrivateMessage}"
}

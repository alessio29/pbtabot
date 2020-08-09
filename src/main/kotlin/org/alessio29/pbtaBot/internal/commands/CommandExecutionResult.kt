package org.alessio29.pbtaBot.internal.commands

class CommandExecutionResult {
    var result = ""
    var toSkip = 0
        private set
    var isPrivateMessage = false
        private set

    constructor(string: String, i: Int) {
        result = string
        toSkip = i
    }

    constructor() {}
    constructor(message: String, count: Int, privateMessage: Boolean) {
        result = message
        toSkip = count
        isPrivateMessage = privateMessage
    }

    override fun toString(): String {
        return "CommandExecutionResult{" +
                "result='" + result + '\'' +
                ", toSkip=" + toSkip +
                ", privateMessage=" + isPrivateMessage +
                '}'
    }
}

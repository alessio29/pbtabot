package org.alessio29.pbtaBot.commands

enum class CommandCategory {
    CARDS, CHARACTERS, DICE, INFO, ADMIN, TOKENS, STATES, MUSIC, OTHER;

    companion object {
        fun valueOfOrNull(string: String): CommandCategory? {
            return try {
                valueOf(string)
            } catch (e: IllegalArgumentException) {
                null
            }
        }
    }
}

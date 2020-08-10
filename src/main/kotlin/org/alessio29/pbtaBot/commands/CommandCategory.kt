package org.alessio29.pbtaBot.commands

enum class CommandCategory {
    CARDS, CHARACTERS, DICE, BENNIES, INITIATIVE, INFO, ADMIN, TOKENS, STATES, MUSIC, OTHER;

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

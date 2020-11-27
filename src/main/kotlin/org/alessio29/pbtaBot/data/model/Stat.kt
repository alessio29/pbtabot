package org.alessio29.pbtaBot.data.model

data class Stat(
    override val name: String,
    val shortcut: String,
    val value: Byte
) : NamedEntity()
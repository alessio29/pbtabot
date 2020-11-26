package org.alessio29.pbtaBot.data.model

data class Playbook(
    override val name: String,
    val description: String,
    var moves: List<Move> = listOf()
): NamedEntity()
package org.alessio29.pbtaBot.data.model

data class Hack(
    override val name: String,
    val shortcut: String,
    var stats: List<Stat> = listOf(),
    var basicMoves: List<Move> = listOf(),
    var playbooks: List<Playbook> = listOf()
): NamedEntity()
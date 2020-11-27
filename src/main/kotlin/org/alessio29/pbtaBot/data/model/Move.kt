package org.alessio29.pbtaBot.data.model

data class Move(
    override val name: String,
    val shortcut: String?,
    val description: String?,
    val strongHitDescription: String?,
    val weakHitDescription: String?,
    val missDescription: String?,
    var stat: Stat? = null
) : NamedEntity()
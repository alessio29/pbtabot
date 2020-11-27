package org.alessio29.pbtaBot.data.model

data class Tag(
    override val name: String,
    val description: String
) : NamedEntity()

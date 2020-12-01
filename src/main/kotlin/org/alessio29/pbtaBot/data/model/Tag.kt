package org.alessio29.pbtaBot.data.model

data class Tag(
    override val name: String,
    val description: String
) : NamedEntity() {
    // This constructor is required for Jackson only.
    // TODO custom deserializer for Kotlin data classes
    constructor() : this("", "")
}

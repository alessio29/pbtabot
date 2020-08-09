package org.alessio29.pbtaBot.internal.commands.interfaces.annotations

import org.alessio29.pbtaBot.internal.commands.CommandCategory
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
annotation class CommandCallback(
    val name: String,
    val category: CommandCategory = CommandCategory.OTHER,
    val description: String,
    val aliases: Array<String>,
    val arguments: Array<String>
)
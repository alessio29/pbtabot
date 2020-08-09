package org.alessio29.pbtaBot.internal.commands.interfaces.annotations

import org.alessio29.pbtaBot.internal.commands.CommandCategory
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

@Retention(RetentionPolicy.RUNTIME)
@Target(AnnotationTarget.ANNOTATION_CLASS, AnnotationTarget.CLASS)
annotation class CommandCategoryOwner(val value: CommandCategory)

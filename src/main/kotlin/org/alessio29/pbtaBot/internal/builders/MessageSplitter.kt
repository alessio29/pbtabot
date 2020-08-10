package org.alessio29.pbtaBot.internal.builders

import java.util.*

object MessageSplitter {

    fun splitMessage(message: String, partLengthLimit: Int): List<String> {
        if (message.length < partLengthLimit) {
            return listOf(message)
        }
        val result: MutableList<String> = ArrayList()
        val partBuilder = StringBuilder()
        for (line in message.split(ReplyBuilder.NEWLINE).toTypedArray()) {
            val trimmedLine = line.trim { it <= ' ' }
            when {
                trimmedLine.length > partLengthLimit -> {
                    if (partBuilder.isNotEmpty()) {
                        result.add(partBuilder.toString().trim { it <= ' ' })
                        partBuilder.setLength(0)
                    }
                    result.addAll(splitLine(trimmedLine, partLengthLimit))
                }
                partBuilder.length + trimmedLine.length + 1 > partLengthLimit -> {
                    result.add(partBuilder.toString().trim { it <= ' ' })
                    partBuilder.setLength(0)
                    partBuilder.append(trimmedLine).append(ReplyBuilder.NEWLINE)
                }
                else -> partBuilder.append(trimmedLine).append(ReplyBuilder.NEWLINE)
            }
        }
        if (partBuilder.isNotEmpty()) {
            result.add(partBuilder.toString().trim { it <= ' ' })
        }
        return result
    }

    private fun splitLine(line: String, partLengthLimit: Int): Collection<String> {
        if (line.length < partLengthLimit) {
            return listOf(line)
        }
        val result: MutableList<String> = ArrayList()
        var lineRemainder = line
        while (lineRemainder.length > partLengthLimit) {
            result.add(lineRemainder.substring(0, partLengthLimit).trim { it <= ' ' })
            lineRemainder = lineRemainder.substring(partLengthLimit)
        }
        result.add(lineRemainder)
        return result
    }
}

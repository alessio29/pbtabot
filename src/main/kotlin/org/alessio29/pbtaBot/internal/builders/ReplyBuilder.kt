package org.alessio29.pbtaBot.internal.builders

import net.dv8tion.jda.api.entities.User
import org.apache.commons.lang3.StringUtils
import java.util.*


class ReplyBuilder {
    private val builder: StringBuilder
    fun leftPad(str: String?, size: Int): ReplyBuilder {
        builder.append(StringUtils.leftPad(str, size))
        return this
    }

    fun tab(): ReplyBuilder {
        builder.append(TAB)
        return this
    }

    fun newLine(): ReplyBuilder {
        builder.append(NEWLINE)
        return this
    }

    fun rightPad(str: String?, size: Int): ReplyBuilder {
        builder.append(StringUtils.rightPad(str, size))
        return this
    }

    fun attach(str: String?): ReplyBuilder {
        builder.append(str)
        return this
    }

    override fun toString(): String {
        return builder.toString()
    }

    fun blockQuote(): ReplyBuilder {
        builder.append(BLOCK_MARKER)
        return this
    }

    fun space(): ReplyBuilder {
        builder.append(SPACE)
        return this
    }

    fun addSquareBrackets(value: String?): ReplyBuilder {
        builder.append(SQUARE_BRACKET_OPEN).append(value)
            .append(SQUARE_BRACKET_CLOSE)
        return this
    }

    companion object {
        const val NEWLINE = "\n"
        const val SPACE = " "
        private const val BLOCK_MARKER = "```"
        private const val QUOTE_MARKER = ">"
        private const val SQUARE_BRACKET_OPEN = "["
        private const val SQUARE_BRACKET_CLOSE = "]"
        private const val TAB = "\t"
        fun removeBlocks(rawMessage: String): String {
            var rawMessage = rawMessage
            while (rawMessage.contains(BLOCK_MARKER)) {
                val blockStarts = rawMessage.indexOf(BLOCK_MARKER)
                val blockStops = rawMessage.indexOf(BLOCK_MARKER, blockStarts + 1)
                if (blockStops == -1) {
                    return rawMessage
                }
                rawMessage = rawMessage.substring(0, blockStarts) + rawMessage.substring(blockStops + 3)
            }
            return rawMessage
        }

        fun removeQuotes(rawMessage: String): String {
            val lines = rawMessage.split(NEWLINE).toTypedArray()
            val res = ArrayList<String>()
            for (line in lines) {
                if (!line.startsWith(QUOTE_MARKER)) {
                    res.add(line)
                }
            }
            return java.lang.String.join(NEWLINE, res)
        }

        fun mention(user: User): String {
            return user.getAsMention()
        }

        fun bold(message: String): String {
            return "**$message**"
        }

        fun bold(value: Int): String {
            return bold(Integer.toString(value))
        }

        fun italic(message: String): String {
            return "*$message*"
        }

        fun underlined(message: String): String {
            return "__" + message + "__"
        }

        fun strikeout(message: String): String {
            return "~~$message~~"
        }

        fun capitalize(charName: String): String {
            return charName.substring(0, 1).toUpperCase() + charName.substring(1)
        }

        fun bold(list: Array<String>): List<String> {
            val res: MutableList<String> = ArrayList()
            for (s in list) {
                res.add(bold(s))
            }
            return res
        }

        fun createNameFromArgs(args: Array<String?>, startFrom: Int): String {
            val charName = ReplyBuilder()
            for (i in startFrom until args.size) {
                charName.space().attach(args[i])
            }
            return charName.toString().trim { it <= ' ' }
        }
    }

    init {
        builder = StringBuilder()
    }
}
package org.alessio29.pbtaBot.eval

import org.alessio29.pbtaBot.grammar.PbtaBotLexer
import org.alessio29.pbtaBot.grammar.PbtaBotParser
import org.antlr.v4.runtime.*

class SyntaxErrorException(message: String?) : RuntimeException(message)

private object ThrowingErrorListener : BaseErrorListener() {
    override fun syntaxError(
        recognizer: Recognizer<*, *>?,
        offendingSymbol: Any?,
        line: Int,
        charPositionInLine: Int,
        msg: String?,
        e: RecognitionException?
    ) {
        throw SyntaxErrorException("[$charPositionInLine]: $msg")
    }
}

private fun <T : Recognizer<*, *>> T.throwOnError() =
    apply {
        removeErrorListeners()
        addErrorListener(ThrowingErrorListener)
    }

fun createParser(input: String): PbtaBotParser {
    val lexer = PbtaBotLexer(CharStreams.fromString(input)).throwOnError()
    return PbtaBotParser(BufferedTokenStream(lexer)).throwOnError()
}

inline fun <T> parseString(input: String, fn: PbtaBotParser.() -> T) =
    createParser(input).fn()
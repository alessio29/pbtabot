package org.alessio29.pbtaBot

import org.alessio29.pbtaBot.eval.parseString
import org.junit.Assert
import org.junit.Test

class TestGrammar {
    @Test
    fun testSimple() {
        Assert.assertEquals(
            "(expression (term 123))",
            parseExprToStringTree("123")
        )
    }

    @Test
    fun testPriority() {
        Assert.assertEquals(
            "(expression (expression (term 2)) + (expression (expression (term 2)) * (expression (term 2))))",
            parseExprToStringTree("2+2*2")
        )
    }

    private fun parseExprToStringTree(input: String) =
        parseString(input) { expression().toStringTree(this) }

}
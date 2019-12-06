package edu.drexel.se575.contract

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class InterpreterTest {

    @Test
    fun `checks that the return value is correct`() {
        val result = Interpreter().runContract("5-0-1|5-1-1|1-2-1|12-2")
        assertEquals(result, 1.toFloat())
    }

    @Test
    fun `checks that a compiled contract will have the correct result`() {
        val result = compile("""
            add 0 1
            add 1 1
            mov 2 1
            ret 2
        """.trimIndent())
        assertEquals(Interpreter().runContract(result), 1.toFloat())
    }
}
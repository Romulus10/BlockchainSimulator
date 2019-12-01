package edu.drexel.se575.contract

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class CompilerTest {

    @Test fun `compiling produces expected intermediate output`() {
        val result = compile("""
            add 0 1
            add 1 1
            mov 1 2
        """.trimIndent())
        assertEquals(result, "5-0-1|5-1-1|1-1-2|")
    }
}
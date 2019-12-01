package edu.drexel.se575.contract

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class InterpreterTest {

    @Test fun `checks that the return value is correct`() {
        val result = Interpreter().runContract("5-0-1|5-1-1|1-2-1|12-2")
        assertEquals(result, 1)
    }
}
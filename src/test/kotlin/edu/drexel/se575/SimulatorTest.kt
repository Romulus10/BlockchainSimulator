package edu.drexel.se575

import org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BlockchainSimulatorTests {
    @Test fun `1 + 1 = 2`() {
        assertEquals(2, 1+1, "1 + 1 should equal 2")
    }
}

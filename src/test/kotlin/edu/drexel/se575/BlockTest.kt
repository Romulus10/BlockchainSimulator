package edu.drexel.se575

import org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class BlockTests {
    @Test fun `mintNewBlock produces a valid block with the correct size`() {
        val blockTxList: Array<Transaction> = arrayOf(Transaction("test","test","Text TX"))
        val block: Block = mintNewBlock(blockTxList)
        assertEquals(block.txCount, 1)
    }
}

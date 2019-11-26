package edu.drexel.se575

import org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.Test

class BlockTests {
    @Test
    fun `mintNewBlock produces a valid block with the correct size`() {
        val blockTxList: Array<Transaction> = arrayOf(Transaction("test", "test", "Text TX"))
        val block: Block = mintNewBlock(blockTxList, "test", "test", "000")
        assertEquals(block.transactionArray.size, 1)
    }
}

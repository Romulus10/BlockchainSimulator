package edu.drexel.se575

import org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.Test

class BlockTests {
    private val keyPair = generateKeyPair()

    @Test
    fun `mintNewBlock produces a valid block with the correct size`() {
        val blockTxList: Array<Transaction> = arrayOf(Transaction("test", "test", 9.toFloat(), keyPair!!.public))
        val block = Block(blockTxList, "test", "test", "000")
        assertEquals(block.transactions.size, 1)
    }
}

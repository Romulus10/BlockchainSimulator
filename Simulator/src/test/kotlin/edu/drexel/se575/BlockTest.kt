package edu.drexel.se575

import org.junit.jupiter.api.Assertions.assertEquals

import org.junit.jupiter.api.Test

class BlockTests {
    private val keyPair = generateKeyPair()

    @Test
    fun `mintNewBlock produces a valid block with the correct size`() {
        val blockTxList: Array<Transaction> = arrayOf(Transaction("test", "test", "Text TX", keyPair!!.public))
        val block = Block(blockTxList, "test", "test", "000")
        assertEquals(block.transactions.size, 1)
    }

    @Test fun getTime() {
    }

    @Test fun setTime() {
    }

    @Test fun mintNewBlock() {
    }

    @Test fun getHash() {
    }

    @Test fun setHash() {
    }

    @Test fun getTxCount() {
    }

    @Test fun setTxCount() {
    }

    @Test fun getTxList() {
    }

    @Test fun setTxList() {
    }
}

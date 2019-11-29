package edu.drexel.se575.data_storage

import edu.drexel.se575.BlockChain
import edu.drexel.se575.Transaction
import org.junit.jupiter.api.Test

class JSONParserTest {

    private val testBlockChain = BlockChain()
    private val testTransaction = Transaction("To string", "From string", "My data string")

    init {
        repeat(45) {
            testBlockChain.addTransactionToQueue(testTransaction)
        }
    }

    @Test
    fun `write blockchain generates file`() {
        writeBlockChainToFile(testBlockChain)
        assert(BLOCK_CHAIN_STORAGE_FILE.exists())
    }

    @Test
    fun `read blockchain from generated file`() {
        writeBlockChainToFile(testBlockChain)
        val readBlockChain = readBlockChainFromFile()

        assert(readBlockChain.size == 10)
    }
}
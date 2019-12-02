package edu.drexel.se575.data_storage

import edu.drexel.se575.Account
import edu.drexel.se575.BlockChain
import edu.drexel.se575.Transaction
import edu.drexel.se575.generateKeyPair
import org.junit.jupiter.api.Test

class JSONParserTest {

    private val testBlockChain = BlockChain()
    private val testTransaction = Transaction("To string", "From string", "My data string",
            generateKeyPair()!!.public)
    private val testAccount = Account()

    init {
        repeat(9) {
            fundAccount()
            testBlockChain.stakeCoins(testAccount, 99.toFloat())
            repeat(5) {
                testBlockChain.addTransactionToQueue(testTransaction)
            }
        }
    }

    private fun fundAccount() {
        testAccount.balance = 100.toFloat()
    }

    @Test
    fun `write blockchain generates file`() {
        fundAccount()
        writeBlockChainToFile(testBlockChain)
        assert(BLOCK_CHAIN_STORAGE_FILE.exists())
    }

    @Test
    fun `read blockchain from generated file`() {
        fundAccount()
        writeBlockChainToFile(testBlockChain)
        val readBlockChain = readBlockChainFromFile()
        assert(readBlockChain.size == 10)
        assert(readBlockChain.isValid())
    }
}
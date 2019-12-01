package edu.drexel.se575.data_storage

import edu.drexel.se575.Cryptography
import edu.drexel.se575.Transaction
import org.junit.jupiter.api.Test

class BlockChainTest {

    private val keyPair = Cryptography().generateKeyPair()
    private val testTransaction = Transaction("To string", "From string", "My data string", keyPair!!.public)

    init {
        testTransaction.sign(keyPair!!.private)
    }

    @Test
    fun `empty blockchain inits with one empty block`(){
        val blockChain = BlockChain()
        assert(blockChain.size == 1)
    }

    @Test
    fun `test 5 transactions makes a block`(){
        val blockChain = BlockChain()

        //start with one block from empty init
        repeat(4){
            blockChain.addTransactionToQueue(testTransaction)
            assert(blockChain.size == 1)
        }
        //fifth transaction creates a block
        blockChain.addTransactionToQueue(testTransaction)
        assert(blockChain.size == 2)

        //repeat above just to be extra confident
        repeat(4){
            blockChain.addTransactionToQueue(testTransaction)
            assert(blockChain.size == 2)
        }
        blockChain.addTransactionToQueue(testTransaction)
        assert(blockChain.size == 3)
    }

    @Test
    fun `make a valid blockchain with 10 blocks and check is valid`(){
        val blockChain = BlockChain()

        repeat(45){
            blockChain.addTransactionToQueue(testTransaction)
        }
        assert(blockChain.size == 10)

        assert(blockChain.isValid())
    }

    @Test
    fun `edit a blockchain with 10 blocks and find not valid`(){
        val blockChain = BlockChain()

        repeat(45){
            blockChain.addTransactionToQueue(testTransaction)
        }
        assert(blockChain.size == 10)

        val myPhonyTransaction = Transaction("me", "you", "transfers 1 MILLION dollars!",
                Cryptography().generateKeyPair()!!.public)

        blockChain.blockList[4].transactions[1]=myPhonyTransaction

        assert(! blockChain.isValid())
    }
}
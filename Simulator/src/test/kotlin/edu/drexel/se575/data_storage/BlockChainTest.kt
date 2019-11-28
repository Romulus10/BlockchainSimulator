package edu.drexel.se575.data_storage

import edu.drexel.se575.Transaction
import org.junit.jupiter.api.Test

class BlockChainTest {

    @Test
    fun `empty blockchain inits with one empty block`(){
        val blockChain = BlockChain()
        assert(blockChain.size == 1)
    }

    @Test
    fun `test 5 transactions makes a block`(){
        val blockChain = BlockChain()
        val test_transaction = Transaction("To string", "From string", "My data string")

        //start with one block from empty init
        repeat(4){
            blockChain.addTransactionToQueue(test_transaction)
            assert(blockChain.size == 1)
        }
        //fifth transaction creates a block
        blockChain.addTransactionToQueue(test_transaction)
        assert(blockChain.size == 2)

        //repeat above just to be extra confident
        repeat(4){
            blockChain.addTransactionToQueue(test_transaction)
            assert(blockChain.size == 2)
        }
        blockChain.addTransactionToQueue(test_transaction)
        assert(blockChain.size == 3)
    }
}
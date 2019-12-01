package edu.drexel.se575

import org.junit.jupiter.api.Test

class BlockChainTest {

    private val keyPair = generateKeyPair()
    private val testTransaction = Transaction("To string", "From string", "My data string", keyPair!!.public)


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
                generateKeyPair()!!.public)

        blockChain.blockList[4].transactions[1]=myPhonyTransaction

        assert(! blockChain.isValid())
    }

    @Test
    fun `replace blockchain with a valid chain`(){
        val myBlockChain = BlockChain()
        val otherBlockChain = BlockChain()

        repeat(45){
            otherBlockChain.addTransactionToQueue(testTransaction)
        }
        assert(otherBlockChain.isValid())

        myBlockChain.replaceChain(otherBlockChain)

        assert(myBlockChain.size == 10)
        assert(myBlockChain.isValid())
    }

    @Test
    fun `fail to replace blockchain with invalid chain`(){
        val myBlockChain = BlockChain()
        val otherBlockChain = BlockChain()

        assert(myBlockChain.size == 1)

        repeat(45){
            otherBlockChain.addTransactionToQueue(testTransaction)
        }

        otherBlockChain.blockList[3].transactions[1].to = "ILLEGAL_NEW_RECIPIENT"
        myBlockChain.replaceChain(otherBlockChain)

        assert(myBlockChain.size == 1)

    }

}
package edu.drexel.se575

import org.junit.jupiter.api.Test

class BlockChainTest {

    private val keyPair = generateKeyPair()
    private val testTransaction = Transaction("To string", "From string", "My data string", keyPair!!.public)

    private val acctA = Account()
    private val acctB = Account()

    init {
        getMoney()
    }

    private fun getMoney() {
        acctA.balance = 100.toFloat()
        acctB.balance = 100.toFloat()
    }

    private fun make9Blocks(blockChain: BlockChain) {
        repeat(9) {
            getMoney()
            blockChain.stakeCoins(acctA, 99.toFloat())
            repeat(5) {
                blockChain.addTransactionToQueue(testTransaction)
            }
        }
    }

    @Test
    fun `empty blockchain inits with one empty block`() {
        val blockChain = BlockChain()
        assert(blockChain.size == 1)
    }

    @Test
    fun `test 5 transactions makes a block`() {
        val blockChain = BlockChain()
        getMoney()

        blockChain.stakeCoins(acctA, 99.0.toFloat())
        blockChain.stakeCoins(acctB, 1.toFloat())

        //start with one block from empty init
        repeat(4) {
            blockChain.addTransactionToQueue(testTransaction)
            assert(blockChain.size == 1)
        }
        //fifth transaction creates a block
        blockChain.addTransactionToQueue(testTransaction)
        assert(blockChain.size == 2)

        //repeat above just to be extra confident
        repeat(4) {
            blockChain.addTransactionToQueue(testTransaction)
            assert(blockChain.size == 2)
        }
        blockChain.addTransactionToQueue(testTransaction)
        assert(blockChain.size == 3)
    }


    @Test
    fun `make a valid blockchain with 10 blocks and check is valid`() {
        val blockChain = BlockChain()
        getMoney()

        make9Blocks(blockChain)

        assert(blockChain.size == 10)

        assert(blockChain.isValid())
    }

    @Test
    fun `edit a blockchain with 10 blocks and find not valid`() {
        val blockChain = BlockChain()
        getMoney()

        make9Blocks(blockChain)
        assert(blockChain.size == 10)


        val myPhonyTransaction = Transaction("me", "you", "transfers 1 MILLION dollars!",
                generateKeyPair()!!.public)

        blockChain.blockList[4].transactions[1] = myPhonyTransaction

        assert(!blockChain.isValid())
    }

    @Test
    fun `replace blockchain with a valid chain`() {
        val myBlockChain = BlockChain()
        val otherBlockChain = BlockChain()
        getMoney()


        make9Blocks(otherBlockChain)


        assert(otherBlockChain.isValid())

        myBlockChain.replaceChain(otherBlockChain)

        assert(myBlockChain.size == 10)
        assert(myBlockChain.isValid())
    }

    @Test
    fun `fail to replace blockchain with invalid chain`() {
        val myBlockChain = BlockChain()
        val otherBlockChain = BlockChain()

        assert(myBlockChain.size == 1)

        make9Blocks(otherBlockChain)

        otherBlockChain.blockList[3].transactions[1].to = "ILLEGAL_NEW_RECIPIENT"
        myBlockChain.replaceChain(otherBlockChain)

        assert(myBlockChain.size == 1)
    }


    @Test
    fun `staking coins actually costs money`() {
        val blockChain = BlockChain()
        getMoney()

        blockChain.stakeCoins(acctA, 10.toFloat())

        assert(acctA.balance == 90.toFloat())
    }

    @Test
    fun `pay previous minter`() {
        val testBlockChain = BlockChain()
        val testAccount = Account()
        testAccount.balance = 5.toFloat()

        testBlockChain.stakeCoins(testAccount, 5.toFloat())
        assert(testAccount.balance == 0.toFloat())

        repeat(5) {
            testBlockChain.addTransactionToQueue(testTransaction)
        }
        val testAccountB = Account()
        testAccountB.balance = 5.toFloat()
        testBlockChain.stakeCoins(testAccountB, 3.toFloat())
        assert(testAccountB.balance == 2.toFloat())

        repeat(5) {
            testBlockChain.addTransactionToQueue(testTransaction)
        }

        assert(testAccount.balance == 10.toFloat() && testAccountB.balance == 2.toFloat())
    }
}
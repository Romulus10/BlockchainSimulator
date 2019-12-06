package edu.drexel.se575

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.lang.IllegalArgumentException

class BlockChainTest {

    private val keyPair = generateKeyPair()
    private val testTransaction = Transaction("To string", "From string", 5.toFloat(), keyPair!!.public)

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
        
        var chainInitialSize = blockChain.size

        blockChain.stakeCoins(acctA, 99.0.toFloat())
        blockChain.stakeCoins(acctB, 1.toFloat())

        //start with one block from empty init
        repeat(TX_PER_BLOCK - blockChain.listTransactionQueue().size - 1) {
            blockChain.addTransactionToQueue(testTransaction)
            assert(blockChain.size == chainInitialSize)
        }
        //fifth transaction creates a block
        blockChain.addTransactionToQueue(testTransaction)
        assert(blockChain.size == chainInitialSize + 1)

        chainInitialSize = blockChain.size
        
        //repeat above just to be extra confident
        repeat(TX_PER_BLOCK - blockChain.listTransactionQueue().size - 1 ) {
            blockChain.addTransactionToQueue(testTransaction)
            assert(blockChain.size == chainInitialSize)
        }
        blockChain.addTransactionToQueue(testTransaction)
        assert(blockChain.size == chainInitialSize + 1)
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


        val myPhonyTransaction = Transaction("me", "you", 5.toFloat(),
                generateKeyPair()!!.public)

        blockChain.blockList[4].transactions[0] = myPhonyTransaction
        blockChain.blockList[4].transactions[0] = myPhonyTransaction

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

        otherBlockChain.blockList[3].transactions[0].to = "ILLEGAL_NEW_RECIPIENT"
        myBlockChain.replaceChain(otherBlockChain)

        assert(myBlockChain.size == 1)
    }


    @Test
    fun `staking coins actually costs money`() {
        val blockChain = BlockChain()
        getMoney()

        blockChain.stakeCoins(acctA, 10.toFloat())

        assert(acctA.balance == 90.toFloat())
        assert (acctA.currentStakedCoins == 10.toFloat())
    }


    @Test
    fun `no over staking`(){
        val blockChain = BlockChain()
        getMoney()
        val amountToStake = acctA.balance
        blockChain.stakeCoins(acctA, amountToStake)
        assertThrows<IllegalArgumentException> { blockChain.stakeCoins(acctA, amountToStake) }
        assertThrows<IllegalArgumentException> { blockChain.stakeCoins(acctA, 0.toFloat()) }
        assert(acctA.currentStakedCoins == amountToStake)
        assert(blockChain.listTransactionQueue().size == 1)

    }

    @Test
    fun `pay previous minter`() {
        val testBlockChain = BlockChain()
        val testAccount = Account()
        //testAccount.balance = 5.toFloat()
        val initialBalance = testAccount.balance

        testBlockChain.stakeCoins(testAccount, initialBalance)
        assert(testAccount.balance == 0.toFloat())
        assert(testAccount.currentStakedCoins == initialBalance)

        //staking coins is a transaction, do the rest to make a block
        repeat(TX_PER_BLOCK - 1) {
            testBlockChain.addTransactionToQueue(testTransaction)
        }
        val testAccountB = Account()
        //testAccountB.balance = 5.toFloat()
        testBlockChain.stakeCoins(testAccountB, initialBalance - 2)
        assert(testAccountB.balance == 2.toFloat())

        repeat(TX_PER_BLOCK - 1) {
            testBlockChain.addTransactionToQueue(testTransaction)
        }

        assert(testAccount.balance == initialBalance + TX_PER_BLOCK &&
                testAccount.currentStakedCoins == 0.toFloat() &&
                testAccountB.balance == 2.toFloat())

        assert(testBlockChain.listTransactionQueue().size == 1)
    }


    @Test
    fun `pay previous minter sum`() {
        val testBlockChain = BlockChain()
        val testAccountA = Account()
        val testAccountB = Account()
        val initialBalance = testAccountA.balance

        repeat(5){
            testBlockChain.stakeCoins(testAccountA, initialBalance/5)
        }

        //TODO actually test



    }


    @Test
    fun `mess up block`() {
        val testBlockChain = BlockChain()
        val testAccount = Account()
        testAccount.balance = 5.toFloat()

        testBlockChain.stakeCoins(testAccount, 5.toFloat())
        assert(testAccount.balance == 0.toFloat())


        make9Blocks(testBlockChain)

        assert(testBlockChain.isValid())

        testBlockChain.messUpBlock(2)
        assert(!testBlockChain.isValid())

        val bad = testBlockChain.findBrokenBlock()
        assert(bad == 2)
    }
}

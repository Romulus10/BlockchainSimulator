package edu.drexel.se575.data_storage

import edu.drexel.se575.Block
import edu.drexel.se575.Transaction
import edu.drexel.se575.mintNewBlock
import edu.drexel.se575.mintStartingBlock

class BlockChain{

    private var transactionQueue = TransactionQueue(this)

    private var blockList = arrayListOf<Block>()

    val size: Int
        get() = blockList.size


    init {
        blockList.add(mintStartingBlock())
    }

    fun addTransactionToQueue(transaction: Transaction){
        transactionQueue.addTransaction(transaction)
    }


    //TODO replace empty validator/signature values
    fun mintBlockIfOverFiveTx(){
        val blockTx = transactionQueue.getTransactionsForBlock()
        val newBlock = mintNewBlock(blockTx, "TEMP VALIDATOR", "TEMP SIGNATURE",
                blockList.last().hash_of_transactions)

        blockList.add(newBlock)
    }


    override fun toString(): String {
        return blockList.joinToString (separator = "\n"){ it -> it.toString() }
    }
}
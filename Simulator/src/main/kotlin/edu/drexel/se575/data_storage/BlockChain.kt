package edu.drexel.se575.data_storage

import edu.drexel.se575.Block
import edu.drexel.se575.Transaction

import edu.drexel.se575.mintStartingBlock
import edu.drexel.se575.verify
import java.lang.IllegalArgumentException

class BlockChain {

    private var transactionQueue = TransactionQueue(this)

    //Not making private, this lets us edit blocks illegally  for demonstration purposes
    var blockList = arrayListOf<Block>()

    val size: Int
        get() = blockList.size


    init {
        blockList.add(mintStartingBlock())
    }

    fun addTransactionToQueue(transaction: Transaction) {
        if (transaction.signature.isNullOrEmpty()){
            throw IllegalArgumentException("The Transaction must be signed before adding to block chain")
        }

        val isValidTransaction = verify(transaction.toString(),
                transaction.signature, transaction.publicKey)

        if (isValidTransaction) {
            transactionQueue.addTransaction(transaction)
        }
        else {
            throw IllegalStateException("The transaction signature is invalid!")
        }
    }


    //TODO replace empty validator/signature values
    fun mintBlockIfOverFiveTx() {
        val blockTx = transactionQueue.getTransactionsForBlock()


        val newBlock = Block(blockTx, "TEMP VALIDATOR", "TEMP SIGNATURE",
                blockList.last().hash)

        blockList.add(newBlock)
    }


    override fun toString(): String {
        return blockList.joinToString(separator = "\n") { it.print() }
    }

    private fun checkBlock(blockInvestigating: Block, previousBlock: Block): Boolean {
        if (previousBlock.hash != blockInvestigating.previousBlockHash) {
            return false
        }

        return true
    }

    fun isValid(): Boolean {
        for (i in 1 until (blockList.size - 1)) {
            val isValid = checkBlock(blockList[i], blockList[i - 1])
            if (!isValid) {
                return false
            }
        }
        return true
    }
}
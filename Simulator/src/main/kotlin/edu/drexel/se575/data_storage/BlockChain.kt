package edu.drexel.se575.data_storage

import edu.drexel.se575.Block
import edu.drexel.se575.Transaction

import edu.drexel.se575.mintStartingBlock

class BlockChain{

    private var transactionQueue = TransactionQueue(this)

    //Not making private, this lets us edit blocks illegally  for demonstration purposes
    var blockList = arrayListOf<Block>()

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
        val newBlock = Block(blockTx, "TEMP VALIDATOR", "TEMP SIGNATURE",
                blockList.last().hash)

        blockList.add(newBlock)
    }


    override fun toString(): String {
        return blockList.joinToString (separator = "\n"){ it -> it.print() }
    }

    fun checkBlock(blockInvestigating: Block, previousBlock: Block): Boolean{
        if (previousBlock.hash != blockInvestigating.previousBlockHash){
            return false
        }

        return true
    }

    fun isValid(): Boolean{
        for (i in 1 until (blockList.size -1)){
            val isValid = checkBlock(blockList[i], blockList[i-1])
            if(!isValid){
                return false
            }
        }
        return true
    }
}
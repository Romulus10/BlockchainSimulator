package edu.drexel.se575

class BlockChain(var blockList: ArrayList<Block> = arrayListOf<Block>()) {

    private var transactionQueue = TransactionQueue(this)

    val size: Int
        get() = blockList.size


    init {
        if (blockList.size == 0) {
            blockList.add(mintStartingBlock())
        }
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

    fun replaceChain(newBlockChain: BlockChain): Boolean{
        if (newBlockChain.isValid()){
            this.blockList = newBlockChain.blockList
            return true
        }
        return false
    }

}
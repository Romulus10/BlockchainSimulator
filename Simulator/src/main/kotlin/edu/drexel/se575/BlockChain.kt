package edu.drexel.se575

import edu.drexel.se575.contract.Interpreter

class BlockChain(var blockList: ArrayList<Block> = arrayListOf()) {

    private var transactionQueue = TransactionQueue(this)
    private var interpreter = Interpreter()

    val size: Int
        get() = blockList.size


    init {
        if (blockList.size == 0) {
            blockList.add(mintStartingBlock())
        }
    }

    fun addTransactionToQueue(transaction: Transaction) {
        interpreter.runContract(transaction.data)
        transactionQueue.addTransaction(transaction)
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
        try {
            blockInvestigating.transactions.forEach {
                interpreter.runContract(it.data)
            }
        } catch (ex: Exception) {
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

    fun replaceChain(newBlockChain: BlockChain): Boolean {
        if (newBlockChain.isValid()) {
            this.blockList = newBlockChain.blockList
            return true
        }
        return false
    }

    fun getBalanceFromAddress(address: String): Int {
        return interpreter.accountList.filter {
            it.address == address
        }[0].weight
    }

    fun listKnownAddresses(): Array<Account> {
        val accountArray = interpreter.accountList.toArray()
        val finalCastArray = accountArray.filterIsInstance<Account>().toTypedArray()
        if (accountArray.size != finalCastArray.size) {
            throw IllegalArgumentException("Array of known addresses has bad value!")
        }
        return finalCastArray
    }

}
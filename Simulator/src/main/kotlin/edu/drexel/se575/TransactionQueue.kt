package edu.drexel.se575

const val TX_PER_BLOCK = 5

class TransactionQueue(private var blockChain: BlockChain) {

    var transactions = arrayListOf<Transaction>()

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)

        if (transactions.size >= TX_PER_BLOCK) {
            blockChain.mintBlockIfOverFiveTx()
        }
    }

    fun getTransactionsForBlock(): Array<Transaction>{
        val blockTx: Array<Transaction> = transactions.take(TX_PER_BLOCK).toTypedArray()
        repeat(TX_PER_BLOCK){
            transactions.removeAt(0)
        }
        return blockTx
    }

}

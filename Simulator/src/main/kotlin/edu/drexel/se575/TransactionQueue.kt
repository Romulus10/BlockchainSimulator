package edu.drexel.se575


class TransactionQueue(private var blockChain: BlockChain) {

    private var transactions = arrayListOf<Transaction>()
    val TX_PER_BLOCK = 5

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)

        if (transactions.size >= TX_PER_BLOCK) {
            blockChain.mintBlockIfOverFiveTx()
        }
    }

    fun getTransactionsForBlock(): Array<Transaction>{
        val blockTx: Array<Transaction> = transactions.take(5).toTypedArray()
        repeat(5){
            transactions.removeAt(0)
        }
        return blockTx
    }

}

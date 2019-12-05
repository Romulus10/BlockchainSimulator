package edu.drexel.se575


class TransactionQueue(private var blockChain: BlockChain) {

    private var transactions = arrayListOf<Transaction>()

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)

        if (transactions.size > 1) {
            println("minting")
            blockChain.mintBlockIfOverFiveTx()
        }
    }

    fun getTransactionsForBlock(): Array<Transaction>{
        val blockTx: Array<Transaction> = transactions.take(1).toTypedArray()
        repeat(1){
            transactions.removeAt(0)
        }
        return blockTx
    }

}
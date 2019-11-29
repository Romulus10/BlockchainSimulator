package edu.drexel.se575.data_storage

import edu.drexel.se575.Transaction


class TransactionQueue(var blockChain: BlockChain){

    var transactions = arrayListOf<Transaction>()

    fun addTransaction(transaction: Transaction){
        transactions.add(transaction)

        if (transactions.size > 4){
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
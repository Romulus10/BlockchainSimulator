package edu.drexel.se575

/**
 *
 */
class Transaction(var to: String, var fr: String, var data: String) {
    var signature: String? = null
    var blockHeight: Int? = null
    var blockNumber: Int? = null

    /**
     *
     */
    fun sign() {
        signature = "SIGNATURE_PLACEHOLDER"
    }

    /**
     *
     */
    override fun toString(): String {
        return "To: %s, From: %s, Data: %s, Block Height: %s, Block Number: %s".format(to, fr, data, blockHeight, blockNumber)
    }
}

fun newTransaction(queue: TransactionQueue, sender: Account, to: Account, data: String) {
    // Run the embedded smart contract.
    val tx = Transaction(to.toString(), sender.toString(), data)
    tx.sign()
    queue.addTransaction(tx)
}
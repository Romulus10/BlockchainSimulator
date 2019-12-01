package edu.drexel.se575

import java.security.PrivateKey
import java.security.PublicKey

/**
 *
 */
class Transaction(var to: String, var fr: String, var data: String, var publicKey: PublicKey) {
    var signature: String? = null
    var blockHeight: Int? = null
    var blockNumber: Int? = null

    /**
     *
     */
    fun sign(privateKey: PrivateKey) {
        signature = Cryptography().sign(this.toString(), privateKey)
    }

    /**
     *
     */
    fun verify(): Boolean{
        return Cryptography().verify(this.toString(), this.signature, this.publicKey)
    }

    override fun toString(): String {
        return "To: %s, From: %s, pubkey: %s, Data: %s".format(to, fr, publicKey,  data, blockHeight, blockNumber)
    }
}

//fun newTransaction(queue: TransactionQueue, sender: Account, to: Account, data: String) {
//    // Run the embedded smart contract.
//    val tx = Transaction(to.toString(), sender.toString(), data)
//    tx.sign()
//    queue.addTransaction(tx)
//}
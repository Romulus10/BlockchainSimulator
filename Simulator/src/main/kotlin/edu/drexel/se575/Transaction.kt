package edu.drexel.se575

import java.security.PrivateKey
import java.security.PublicKey
import kotlin.collections.ArrayList

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
        signature = sign(this.toString(), privateKey)
    }

    /**
     *
     */
    fun verify(): Boolean {
        return verify(this.toString(), this.signature, this.publicKey)
    }

    override fun toString(): String {
        return "To: %s, From: %s, pubkey: %s, Data: %s, Block Height: %s, Block Number: %s".format(to, fr, publicKey, data, blockHeight, blockNumber)
    }
}

fun transactionFromString(accountList: ArrayList<Account>, tx: String): Transaction {
    val transactionArrayList = ArrayList<String>()
    val arr = tx.split(',')
    arr.forEach {
        val x = it.split(':')[1].drop(1)
        transactionArrayList.add(x)
    }
    val t = Transaction(transactionArrayList[0], transactionArrayList[1], transactionArrayList[4], getKey(accountList, transactionArrayList[1]))
    t.signature = transactionArrayList[3]
    t.blockHeight = transactionArrayList[5].toInt()
    t.blockNumber = transactionArrayList[6].toInt()
    return t
}

fun getKey(accountList: ArrayList<Account>, address: String): PublicKey {
    val x = accountList.filter { it.address == address }
    val y = x[0].publicKey
    return y
}
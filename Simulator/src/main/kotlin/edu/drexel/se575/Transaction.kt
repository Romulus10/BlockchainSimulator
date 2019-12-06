package edu.drexel.se575

import kotlinx.serialization.*
import java.security.PrivateKey
import java.security.PublicKey

/**
 *
 */
@Serializable class Transaction(var to: String, var fr: String, var data: Float, var publicKey: PublicKey) {
    var signature: String? = null
    var blockHeight: Int? = null
    var blockNumber: Int? = null
    var timeCreated = System.currentTimeMillis()

    /**
     *
     */
    fun sign(privateKey: PrivateKey) {
        signature = sign(this.toString(), privateKey)
    }

    /**
     *
     */
    fun verify(): Boolean = verify(this.toString(), this.signature, this.publicKey)

    override fun toString(): String = "To: %s, From: %s, pubkey: %s, Data: %s, Signed time: %s".format(to, fr, publicKey, data, timeCreated)
}

fun transactionFromString(accountList: ArrayList<Account>, tx: String): Transaction {
    val transactionArrayList = ArrayList<String>()
    val arr = tx.split(',')
    arr.forEach {
        val x = it.split(':')[1].drop(1)
        transactionArrayList.add(x)
    }
    val t = Transaction(transactionArrayList[0], transactionArrayList[1], transactionArrayList[4].toFloat(), getKey(accountList, transactionArrayList[1]))
    t.signature = transactionArrayList[3]
    t.blockHeight = transactionArrayList[5].toInt()
    t.blockNumber = transactionArrayList[6].toInt()
    return t
}

fun getKey(accountList: ArrayList<Account>, address: String): PublicKey = accountList.filter { it.address == address }[0].publicKey
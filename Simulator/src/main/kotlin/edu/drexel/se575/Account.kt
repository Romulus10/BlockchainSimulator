package edu.drexel.se575

import java.security.PrivateKey
import java.security.PublicKey

/**
 * A nice, warm place to keep your stuff and run transactions from.
 * @property address The unique address of the account.
 * @property balance The amount of coin held by this account.
 */
class Account {
    var address: String
    var balance: Float = 0.toFloat()
    val privateKey: PrivateKey
    val publicKey: PublicKey

    init {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        address = Util.sha1((1..32)
                .map { kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString(""))
        val keyPair = generateKeyPair()
        privateKey = keyPair!!.private
        publicKey = keyPair.public
    }
}

fun transferAccountValue(accountList: Array<Account?>, to: String?, fr: String?, amount: Int) {
    accountList.forEach {
        it!! // Assert that this object is not null.
        if (it.address == to) {
            it.balance -= amount
        }
        if (it.address == fr) {
            it.balance += amount
        }
    }
}

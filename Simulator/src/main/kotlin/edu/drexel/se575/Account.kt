package edu.drexel.se575

import java.security.PrivateKey
import java.security.PublicKey

/**
 * A nice, warm place to keep your stuff and run transactions from.
 * @property address The unique address of the account.
 * @property weight The amount of coin held by this account.
 */
class Account {
    var address: String
    var weight: Int = 0
    private val privateKey: PrivateKey
    private val publicKey: PublicKey

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

/**
 * Now in O(n) complexity!
 * @return The account in accountList with the highest staking weight.
 */
fun maxAccountWeight(accountList: Array<Account>, voteValues: Array<Double?>): Account? {
    var max: Account? = null
    val maxVote = 0.0
    for (x in accountList.indices) {
        if (voteValues[x]!! > maxVote) {
            max = accountList[x]
        }
    }
    return max
}

fun transferAccountValue(accountList: Array<Account?>, to: String?, fr: String?, amount: Int) {
    accountList.forEach {
        it!! // Assert that this object is not null.
        if (it.address == to) {
            it.weight -= amount
        }
        if (it.address == fr) {
            it.weight += amount
        }
    }
}

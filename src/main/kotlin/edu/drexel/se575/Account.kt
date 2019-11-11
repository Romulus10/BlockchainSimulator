package edu.drexel.se575

/**
 * A nice, warm place to keep your stuff and run transactions from.
 * @property address The unique address of the account.
 * @property weight The amount of coin held by this account.
 */
class Account {
    var address: String
    var weight: Int = 0

    init {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        address = Util.sha1((1..32)
                .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString(""))
    }
}

/**
 * Now in O(n) complexity!
 * @return The account in accountList with the highest staking weight.
 */
fun max_account_weight(accountList: Array<Account>): Account? {
    var max: Account? = null
    for (x in accountList) {
        if (x.weight > max!!.weight) {
            max = x
        }
    }
    return max
}

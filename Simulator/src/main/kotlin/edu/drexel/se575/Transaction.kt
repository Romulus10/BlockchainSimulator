package edu.drexel.se575

import kotlinx.serialization.*

/**
 *
 */
@Serializable class Transaction(var to: String, var fr: String, var data: String) {
    var blockHeight: Int? = null
    var blockNumber: Int? = null

    /**
     *
     */
    override fun toString(): String {
        return "To: %s, From: %s, Data: %s, Block Height: %s, Block Number: %s".format(to, fr, data, blockHeight, blockNumber)
    }
}

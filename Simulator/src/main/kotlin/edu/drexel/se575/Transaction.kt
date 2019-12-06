package edu.drexel.se575

import kotlinx.serialization.Serializable
import java.security.PrivateKey
import java.security.PublicKey

/**
 *
 */
@Serializable
class Transaction(var to: String, var fr: String, var data: Float, var publicKey: PublicKey) {
    var signature: String? = null
    private var timeSigned: Long? = null

    /**
     *
     */
    fun sign(privateKey: PrivateKey) {
        timeSigned = System.currentTimeMillis()
        signature = sign(this.toString(), privateKey)
    }

    /**
     *
     */
    fun verify(): Boolean = verify(this.toString(), this.signature, this.publicKey)

    override fun toString(): String = "To: %s, From: %s, pubkey: %s, Data: %s, Signed time: %s".format(to, fr, publicKey, data, timeSigned)
}
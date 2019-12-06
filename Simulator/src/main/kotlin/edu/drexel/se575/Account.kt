package edu.drexel.se575

import kotlinx.serialization.*
import kotlinx.serialization.internal.HexConverter
import kotlinx.serialization.internal.StringDescriptor
import java.security.PrivateKey
import java.security.PublicKey

/**
 * A nice, warm place to keep your stuff and run transactions from.
 * @property address The unique address of the account.
 * @property balance The amount of coin held by this account.
 */
@Serializable class Account {
    var address: String
    var balance = 100.toFloat()
    val privateKey: PrivateKey
    val publicKey: PublicKey
    var currentStakedCoins = 0.toFloat()

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

    @Serializer(forClass = Account::class)
    companion object : KSerializer<Account> {
        override val descriptor: SerialDescriptor
            get() = StringDescriptor.withName("Account")

        override fun serialize(encoder: Encoder, obj: Account) {
            val compositeOutput = encoder.beginStructure(descriptor)
            compositeOutput.encodeStringElement(descriptor, 0, HexConverter.printHexBinary(obj.address.toByteArray()))
            compositeOutput.encodeStringElement(descriptor, 1, HexConverter.printHexBinary(obj.balance.toString().toByteArray()))
            compositeOutput.endStructure(descriptor)
        }

        override fun deserialize(decoder: Decoder): Account {
            return Account()
        }
    }
}

fun transferAccountValue(accountList: Array<Account?>, to: String?, fr: String?, amount: Int) {
    accountList.forEach {
        it!! // Assert that this object is not null.
        if (it.address == to) {
            it.balance += amount
        }
        if (it.address == fr) {
            it.balance -= amount
        }
    }
}

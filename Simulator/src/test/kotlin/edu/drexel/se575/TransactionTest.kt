package edu.drexel.se575

import org.junit.jupiter.api.Test

internal class TransactionTest {

    private val myKeyPair = Cryptography().generateKeyPair()
    private val testTransaction = Transaction("To string", "From string", "My data string", myKeyPair!!.public)


    @Test fun getBlockHeight() {
    }

    @Test fun setBlockHeight() {
    }

    @Test fun getBlockNumber() {
    }

    @Test fun setBlockNumber() {
    }

    @Test fun testToString() {
    }

    @Test fun getTo() {
    }

    @Test fun setTo() {
    }

    @Test fun getFr() {
    }

    @Test fun setFr() {
    }

    @Test fun getData() {
    }

    @Test fun setData() {
    }

    @Test
    fun `sign transaction`(){
        testTransaction.sign(myKeyPair!!.private)
        assert (testTransaction.signature != null)

        assert(Cryptography().verify(testTransaction.toString(), testTransaction.signature, testTransaction.publicKey))
    }

    @Test
    fun `fail to forge signature transaction`(){
        val otherKeyPair = Cryptography().generateKeyPair()
        val forgedTransaction = Transaction("To me", "From them", "pay $100", otherKeyPair!!.public)
        forgedTransaction.sign(myKeyPair!!.private)

        val isValid = forgedTransaction.verify()
        assert(!isValid)

    }

}
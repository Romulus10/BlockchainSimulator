package edu.drexel.se575

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*


internal class TransactionTest {

    private val myKeyPair = generateKeyPair()
    private val testTransaction = Transaction("To string", "From string", 5.toFloat(), myKeyPair!!.public)

    @Test
    fun `sign transaction`() {
        testTransaction.sign(myKeyPair!!.private)
        assert(testTransaction.signature != null)

        assert(verify(testTransaction.toString(), testTransaction.signature, testTransaction.publicKey))
    }

    @Test
    fun `sign multiples transactions Different Hash`() {
        val testTransactionA = Transaction("To string", "From string", 5.toFloat(), myKeyPair!!.public)
        val testTransactionB = Transaction("To string", "From string", 5.toFloat(), myKeyPair!!.public)
        testTransactionA.sign(myKeyPair!!.private)
        testTransactionB.sign(myKeyPair!!.private)
        assert(testTransactionA.signature != null)
        assert(testTransactionA.signature != testTransactionB.signature)

    }

    @Test
    fun `fail to forge signature transaction`() {
        val otherKeyPair = generateKeyPair()
        val forgedTransaction = Transaction("To me", "From them", 5.toFloat(), otherKeyPair!!.public)
        forgedTransaction.sign(myKeyPair!!.private)

        val isValid = forgedTransaction.verify()
        assert(!isValid)
    }
}
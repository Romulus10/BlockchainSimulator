package edu.drexel.se575

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.util.*


internal class TransactionTest {

    private val myKeyPair = generateKeyPair()
    private val testTransaction = Transaction("To string", "From string", "My data string", myKeyPair!!.public)


    @Test
    fun getBlockHeight() {
    }

    @Test
    fun setBlockHeight() {
    }

    @Test
    fun getBlockNumber() {
    }

    @Test
    fun setBlockNumber() {
    }

    @Test
    fun testToString() {
    }

    @Test
    fun getTo() {
    }

    @Test
    fun setTo() {
    }

    @Test
    fun getFr() {
    }

    @Test
    fun setFr() {
    }

    @Test
    fun getData() {
    }

    @Test
    fun setData() {
    }

    @Test
    fun `sign transaction`() {
        testTransaction.sign(myKeyPair!!.private)
        assert(testTransaction.signature != null)

        assert(verify(testTransaction.toString(), testTransaction.signature, testTransaction.publicKey))
    }

    @Test
    fun `fail to forge signature transaction`() {
        val otherKeyPair = generateKeyPair()
        val forgedTransaction = Transaction("To me", "From them", "pay $100", otherKeyPair!!.public)
        forgedTransaction.sign(myKeyPair!!.private)

        val isValid = forgedTransaction.verify()
        assert(!isValid)
    }

    @Test
    fun `retrieve a correct transaction from a given string`() {
        val encoder: Base64.Encoder = Base64.getEncoder()
        val acctList = ArrayList<Account>()
        acctList.add(Account())
        val initialTransaction = Transaction(acctList[0].address, acctList[0].address, "pay $100", acctList[0].publicKey)
        initialTransaction.sign(acctList[0].privateKey)
        val keyString = "To: ${acctList[0].address}, From: ${acctList[0].address}, pubkey: ${encoder.encodeToString(acctList[0].publicKey.encoded)}, Signature: ${initialTransaction.signature}, Data: pay $100, Block Height: 1, Block Number: 1"
        val secondTransaction = transactionFromString(acctList, keyString)
        assertEquals(initialTransaction.signature, secondTransaction.signature)
    }

}
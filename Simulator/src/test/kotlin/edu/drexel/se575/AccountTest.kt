package edu.drexel.se575

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class AccountTest {

    @Test fun getAddress() {
        val acct = Account()
        acct.address = "some_address"
        assertEquals(acct.address, "some_address")
    }

    @Test fun setAddress() {
        val acct = Account()
        acct.address = "some_address"
        assertEquals(acct.address, "some_address")
    }

    @Test fun getWeight() {
        val acct = Account()
        acct.balance = 1.toFloat()
        assertEquals(acct.balance, 1.toFloat())
    }

    @Test fun setWeight() {
        val acct = Account()
        acct.balance = 1.toFloat()
        assertEquals(acct.balance, 1.toFloat())
    }

    @Test fun maxAccountWeight() {

    }

    @Test fun transferAccountValue() {
    }
}
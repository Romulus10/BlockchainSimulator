package edu.drexel.se575

import java.security.MessageDigest
import javax.xml.bind.DatatypeConverter

/**
 *
 */
object Util {

    /**
     * @returns The SHA-1 hash of [input].
     */
    fun sha1(input: String) = hashString(input)

    /**
     * Mangles the string beyond repair, but we do get a unique identifier out of it.
     * @return The SHA-1 hash of [input]..
     */
    private fun hashString(input: String): String {
        val bytes = MessageDigest
                .getInstance("SHA-1")
                .digest(input.toByteArray())
        return DatatypeConverter.printHexBinary(bytes).toUpperCase()
    }
}

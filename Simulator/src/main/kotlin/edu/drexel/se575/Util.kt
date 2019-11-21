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
    fun sha1(input: String) = hashString("SHA-1", input)

    /**
     * @returns The MD5 hash of [input].
     */
    fun md5(input: String) = hashString("MD5", input)

    /**
     * Mangles the string beyond repair, but we do get a unique identifier out of it.
     * @return The hash of [input] specified by [type].
     */
    private fun hashString(type: String, input: String): String {
        val bytes = MessageDigest
                .getInstance(type)
                .digest(input.toByteArray())
        return DatatypeConverter.printHexBinary(bytes).toUpperCase()
    }
}

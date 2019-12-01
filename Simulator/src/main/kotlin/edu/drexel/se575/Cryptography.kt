package edu.drexel.se575

import java.security.*
import java.util.*
import java.util.Base64.getDecoder

@Throws(Exception::class)
fun generateKeyPair(): KeyPair? {
    val generator = KeyPairGenerator.getInstance("RSA")
    generator.initialize(2048, SecureRandom())
    return generator.generateKeyPair()
}

@Throws(Exception::class)
fun sign(plainText: String, privateKey: PrivateKey?): String? {
    val privateSignature = Signature.getInstance("SHA256withRSA")
    privateSignature.initSign(privateKey)
    privateSignature.update(plainText.toByteArray())
    val signature = privateSignature.sign()
    return Base64.getEncoder().encodeToString(signature)
}

@Throws(Exception::class)
fun verify(plainText: String, signature: String?, publicKey: PublicKey?): Boolean {
    val publicSignature = Signature.getInstance("SHA256withRSA")
    publicSignature.initVerify(publicKey)
    publicSignature.update(plainText.toByteArray())
    val signatureBytes = getDecoder().decode(signature)
    return publicSignature.verify(signatureBytes)
}
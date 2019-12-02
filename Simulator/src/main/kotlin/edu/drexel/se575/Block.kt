package edu.drexel.se575

import java.security.PrivateKey

/**
 * The thing we're chaining together. There's not much more to it than that.
 * @property transactions -- the transactions data
 * @property timeBlockMinted -- time the block object was created
 * @property validator -- Not yet implemented
 * @property signature -- Not yet implemented
 * @property previousBlockHash -- hash of the previous block in the chain
 * @property hash -- sha1 of the timeBlockMinted, previousBlockHash, transactions turned to string and smashed together
 */
class Block(var transactions: Array<Transaction>, var validator: String,
            var signature: String, var previousBlockHash: String) {

  private val timeBlockMinted: Long = System.currentTimeMillis()
  val hash
    get() =  Util.sha1("$timeBlockMinted$previousBlockHash${transactions.joinToString { it.toString() }}")


  fun print(): String{
    return "Block $hash; Minted at: $timeBlockMinted; by $validator. " +
            "Data: ${transactions.joinToString(separator = ",") { "\n\t$it" }}"
  }
}

fun createSignatureForNewBlock(transactions: Array<Transaction>, validatorPrivate: PrivateKey): String{
   sign(transactions.joinToString { "$it " }, validatorPrivate)

}

fun mintStartingBlock(): Block{
  return Block(arrayOf(),"Start", "start", "000")
}
package edu.drexel.se575
/**
 * The thing we're chaining together. There's not much more to it than that.
 * @property hash_of_transactions
 * @property timeBlockMinted
 */
class Block(var hash_of_transactions: String, var transactionArray: Array<Transaction>,
            var validator: String, var signature: String, var previousBlockHash: String) {

  var timeBlockMinted: Long = System.currentTimeMillis()

  override fun toString(): String{
    return "Block $hash_of_transactions; Minted at: $timeBlockMinted; by $validator. " +
            "Data: ${transactionArray.joinToString(separator = ","){ it -> "\n\t${it.toString()}" } }"
  }
}

fun mintStartingBlock(): Block{
  return mintNewBlock(arrayOf(),"Start", "start", "000")
}



/**
 * Every block has to start somewhere.
 * @return A block containing all of the transactions in [txArray].
 */
fun mintNewBlock(txArray: Array<Transaction>, validator: String, signature: String, previousBlockHash: String): Block {
  val hash: String = Util.sha1(txArray.joinToString { it.toString() })
  return Block(hash, txArray, validator, signature, previousBlockHash)
}
package edu.drexel.se575

class Block(hash: String, txCount: Int, txList: Array<Transaction>) {
  private val time: Long =  System.currentTimeMillis()

fun mintNewBlock(txList: Array<Transaction>) -> Block {
  // TODO Generate a hash of the block - should probably implement a ToString for the Transaction object.
  val hash: String = "Placeholder"
  return Block(hash, txList.length(), txList)
}
}

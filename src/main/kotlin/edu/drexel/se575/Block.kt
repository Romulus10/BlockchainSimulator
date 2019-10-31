package edu.drexel.se575

class Block(public var hash: String, public var txCount: Int, public var txList: Array<Transaction>) {
  public var time: Long =  System.currentTimeMillis()
}

fun mintNewBlock(txList: Array<Transaction>): Block {
  // TODO Generate a hash of the block - should probably implement a ToString for the Transaction object.
  val hash: String = "Placeholder"
  return Block(hash, txList.size, txList)
}

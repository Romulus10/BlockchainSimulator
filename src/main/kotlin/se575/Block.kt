package se575

class Block(size: Int, hash: String, txCount: Int, txList: Array<Transaction>) {
  private val time: Long =  System.currentTimeMillis()
}

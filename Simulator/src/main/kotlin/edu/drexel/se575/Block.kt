package edu.drexel.se575

/**
 * The thing we're chaining together. There's not much more to it than that.
 * @property hash
 * @property txCount
 * @property time
 */
class Block(var hash: String, var txCount: Int, var txList: Array<Transaction>) {
  var time: Long = System.currentTimeMillis()

  /**
   * Every block has to start somewhere.
   * @return A block containing all of the transactions in [txList].
   */
  fun mintNewBlock(txList: Array<Transaction>): Block {
    var tmp = ""
    txList.forEach {
      tmp += it.toString()
    }
    val hash: String = Util.sha1(tmp)
    return Block(hash, txList.size, txList)
  }
}
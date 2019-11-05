package edu.drexel.se575

class Block(var hash: String, var txCount: Int, var txList: Array<Transaction>) {
  var time: Long = System.currentTimeMillis()
}

fun mintNewBlock(txList: Array<Transaction>): Block {
  var tmp = ""
  txList.forEach {
    tmp += it.toString()
  }
  val hash: String = Util.sha1(tmp)
  return Block(hash, txList.size, txList)
}

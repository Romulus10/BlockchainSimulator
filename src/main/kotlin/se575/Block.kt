package se575

class Block(size: Int, hash: String, txCount: Int, txList: Array<Transaction>) {
  val time: Long

  init {
    time = getTimeMillis()
  }
}

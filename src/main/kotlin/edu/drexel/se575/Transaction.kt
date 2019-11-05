package edu.drexel.se575

class Transaction(var to: String, var fr: String, var data: String) {
    var block_height: Int? = null
    var block_number: Int? = null

    override fun toString(): String {
        return "To: %s, From: %s, Data: %s, Block Height: %s, Block Number: %s".format(to, fr, data, block_height, block_number)
    }
}

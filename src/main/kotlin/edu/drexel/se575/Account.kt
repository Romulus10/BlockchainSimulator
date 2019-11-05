package edu.drexel.se575

class Account {
    private var address: String

    init {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        address = Util.sha1((1..32)
                .map { i -> kotlin.random.Random.nextInt(0, charPool.size) }
                .map(charPool::get)
                .joinToString(""))
    }
}
package edu.drexel.se575

import io.javalin.Javalin

fun main() {
    println("Starting node...")

    val app = Javalin.create().start(7000)

    app.get("/") { ctx -> ctx.result("Hello World") }

    app.post("/client/transaction/create") { ctx ->
        ctx.result("")
    }

    app.get("/client/transaction/list") { ctx ->
        ctx.result("")
    }

    app.get("/client/transaction/list_by_block/:block_id") { ctx ->
        ctx.result("")
    }

    app.get("/client/transaction/:tx_id") { ctx ->
        ctx.result("")
    }

    app.post("/client/block/create") { ctx ->
        ctx.result("")
    }

    app.get("/client/block/list") { ctx ->
        ctx.result("")
    }

    app.get("/client/block/:tx_id") { ctx ->
        ctx.result("")
    }

    app.get("/client/account/:address") { ctx ->
        ctx.result("")
    }

    app.get("/client/account/list") { ctx ->
        ctx.result("")
    }

    P2PServer(BlockChain(), 5001).listen()
}

package edu.drexel.se575

import io.javalin.Javalin

fun main() {
    println("Starting node...")

    val app = Javalin.create().start(7000)

    app.get("/") { ctx -> ctx.result("Hello World") }

    app.post("/transaction/create") { ctx ->
        ctx.result("")
    }

    app.get("/transaction/list") { ctx ->
        ctx.result("")
    }

    app.get("/transaction/list_by_block/:block_id") { ctx ->
        ctx.result("")
    }

    app.get("/transaction/:tx_id") { ctx ->
        ctx.result("")
    }

    app.post("/block/create") { ctx ->
        ctx.result("")
    }

    app.get("/block/list") { ctx ->
        ctx.result("")
    }

    app.get("/block/:tx_id") { ctx ->
        ctx.result("")
    }
}

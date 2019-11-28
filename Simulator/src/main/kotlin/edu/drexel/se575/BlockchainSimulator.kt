package edu.drexel.se575

import io.javalin.Javalin

fun main(args: Array<String>) {
    println("Starting node...")

    val app = Javalin.create().start(7000)
    app.get("/") { ctx -> ctx.result("Hello World") }
}

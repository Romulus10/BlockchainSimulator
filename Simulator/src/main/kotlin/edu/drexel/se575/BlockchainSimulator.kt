package edu.drexel.se575

import io.javalin.Javalin

fun main() {
    println("Starting node...")

    val app = Javalin.create().start(7000)
    val blockChain = BlockChain()
    val p2pserver = P2PServer(BlockChain(), 5001)

    app.get("/") { ctx ->
        ctx.result("The API is working.")
    }

    app.post("/client/transaction/create") { ctx ->
        val toAddress = ctx.formParam("to")
        val frAddress = ctx.formParam("from")
        val data = ctx.formParam("data")
        val fr = blockChain.interpreter.accountList.filter { it.address == frAddress }[0]
        val tx = Transaction(toAddress!!, frAddress!!, data!!, fr.publicKey)
        tx.sign(fr.privateKey)
        blockChain.addTransactionToQueue(tx)
        p2pserver.sendTransaction(tx)
        ctx.status(200)
    }

    app.post("/client/account/create") {
        val acct = Account()
        blockChain.interpreter.accountList.add(acct)
        val tx = Transaction(acct.address, acct.address, "act", acct.publicKey)
        tx.sign(acct.privateKey)
        blockChain.addTransactionToQueue(tx)
        p2pserver.sendTransaction(tx)
    }

    app.get("/client/transaction/list") { ctx ->
        val txList = ArrayList<Transaction>()
        blockChain.blockList.forEach { b ->
            b.transactions.forEach {
                txList.add(it)
            }
        }
        ctx.json(txList)
    }

    app.get("/client/transaction/list_by_block/:block_id") { ctx ->
        val blockID = ctx.pathParam("block_id")
        ctx.json(blockChain.blockList.filter { it.signature == blockID }[0].transactions)
    }

    app.get("/client/transaction/:tx_id") { ctx ->
        val txID = ctx.pathParam("tx_id")
        val txList = ArrayList<Transaction>()
        blockChain.blockList.forEach { b ->
            b.transactions.forEach {
                txList.add(it)
            }
        }
        ctx.json(txList.filter { txID == it.signature }[0])
    }

    app.get("/client/block/list") { ctx ->
        ctx.json(blockChain.blockList)
    }

    app.get("/client/block/:block_id") { ctx ->
        val blockID = ctx.pathParam("block_id")
        ctx.json(blockChain.blockList.filter { it.hash == blockID }[0])
    }

    app.get("/client/account/:address") { ctx ->
        val address = ctx.pathParam("address")
        ctx.json(blockChain.interpreter.accountList.filter { it.address == address }[0])
    }

    app.get("/client/account/balance/:address") { ctx ->
        val address = ctx.pathParam("address")
        ctx.json(blockChain.interpreter.accountList.filter { it.address == address }[0].balance)
    }

    app.get("/client/account/list") { ctx ->
        ctx.json(blockChain.listKnownAddresses())
    }

    p2pserver.listen()
}

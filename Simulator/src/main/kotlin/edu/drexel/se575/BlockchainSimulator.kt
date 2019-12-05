package edu.drexel.se575

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import kotlinx.serialization.*
import io.javalin.Javalin

@Serializable data class TransactionProposal(val to: String, val fr: String, val data: String)

fun main() {
    println("Starting node...")

    val app = Javalin.create {
        it.enableCorsForAllOrigins()
    }.start(7000)
    val blockChain = BlockChain()

    app.get("/") { ctx ->
        ctx.result("The API is working.")
    }

    app.post("/client/transaction/create") { ctx ->
        val proposal = ctx.body<TransactionProposal>()
        val fr = blockChain.interpreter.accountList.filter { it.address == proposal.to }[0]
        val tx = Transaction(proposal.to, proposal.fr, proposal.data, fr.publicKey)
        tx.sign(fr.privateKey)
        val result = blockChain.addTransactionToQueue(tx)
        ctx.json(result)
    }

    app.post("/client/account/create") {
        val acct = Account()
        blockChain.interpreter.accountList.add(acct)
        val tx = Transaction(acct.address, acct.address, "act", acct.publicKey)
        tx.sign(acct.privateKey)
        blockChain.addTransactionToQueue(tx)
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
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        ctx.json(mapper.writeValueAsString(blockChain.blockList.filter { it.signature == blockID }[0].transactions))
    }

    app.get("/client/transaction/get/:tx_id") { ctx ->
        val txID = ctx.pathParam("tx_id")
        val txList = ArrayList<Transaction>()
        blockChain.blockList.forEach { b ->
            b.transactions.forEach {
                txList.add(it)
            }
        }
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        ctx.result(mapper.writeValueAsString(txList.filter { txID == it.signature }[0]))
    }

    app.get("/client/block/list") { ctx ->
        ctx.json(blockChain.blockList)
    }

    app.get("/client/block/get/:block_id") { ctx ->
        val blockID = ctx.pathParam("block_id")
        ctx.json(blockChain.blockList.filter { it.hash == blockID }[0])
    }

    app.get("/client/account/get/:address") { ctx ->
        val address = ctx.pathParam("address")
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        ctx.result(mapper.writeValueAsString(blockChain.interpreter.accountList.filter { it.address == address }[0]))
    }

    app.get("/client/account/balance/:address") { ctx ->
        val address = ctx.pathParam("address")
        ctx.json(blockChain.interpreter.accountList.filter { it.address == address }[0].balance)
    }

    app.get("/client/account/list") { ctx ->
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        ctx.result(mapper.writeValueAsString(blockChain.listKnownAddresses()))
    }

    app.get("/client/block/clobber/:index") { ctx ->
        blockChain.messUpBlock(ctx.pathParam("index").toInt())
    }

    app.get("/client/account/stake/:address/:amount"){ ctx ->
        val address = ctx.pathParam("address")
        val amount = ctx.pathParam("amount")
        val usrAccount: Account? = null
        blockChain.interpreter.accountList.filter {
            usrAccount == it
        }
        blockChain.stakeCoins(usrAccount!!, amount.toFloat())
    }

}

package edu.drexel.se575

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import io.javalin.Javalin
import kotlinx.serialization.Serializable

@Serializable
data class TransactionProposal(val to: String?, val fr: String?, val data: String?)

fun transactionProposalFromJson(json: String): TransactionProposal {
    val jsonBroken = json.split(',')
    val to = jsonBroken[0].split(':')[1].replace("}", "").replace("\"", "")
    val fr = jsonBroken[1].split(':')[1].replace("}", "").replace("\"", "")
    val data = jsonBroken[2].split(':')[1].replace("}", "").replace("\"", "")
    return TransactionProposal(to, fr, data)
}

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
        val proposal = transactionProposalFromJson(ctx.body())
        val fr = blockChain.interpreter.accountList.filter { it.address == proposal.fr }[0]
        val tx = Transaction(proposal.to!!, proposal.fr!!, proposal.data!!.toFloat(), fr.publicKey)
        tx.sign(fr.privateKey)
        blockChain.addTransactionToQueue(tx)
    }

    app.post("/client/account/create") {
        val acct = Account()
        blockChain.interpreter.accountList.add(acct)

    }

    app.get("/client/transaction/list") { ctx ->
        val txList = ArrayList<Transaction>()
        blockChain.listTransactionQueue().forEach {
            txList.add(it)
        }
        blockChain.blockList.forEach { b ->
            b.transactions.forEach {
                txList.add(it)
            }
        }
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        ctx.result(mapper.writeValueAsString(txList))
    }

    app.get("/client/transaction/list_by_block/:block_id") { ctx ->
        val blockID = ctx.pathParam("block_id")
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        ctx.result(mapper.writeValueAsString(blockChain.blockList.filter { it.signature == blockID }[0].transactions))
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
        val mapper = ObjectMapper()
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        ctx.json(mapper.writeValueAsString(blockChain.blockList))
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
        blockChain.isValid()
        ctx.result(mapper.writeValueAsString(blockChain.listKnownAddresses()))
    }

    app.get("/client/block/clobber/:index") { ctx ->
        blockChain.messUpBlock(ctx.pathParam("index").toInt())
    }

    app.get("/client/account/stake/:address/:amount") { ctx ->
        val address = ctx.pathParam("address")
        val amount = ctx.pathParam("amount")
        val usrAccount = blockChain.interpreter.accountList.filter {
            address == it.address
        }[0]
        blockChain.stakeCoins(usrAccount, amount.toFloat())
    }

    app.get("/client/block/get_if_valid") { ctx ->
        if (!blockChain.isValid()) {
            ctx.result("{ 'valid': false, 'invalid_block': ${blockChain.findBrokenBlock()} }")
        } else {
            ctx.result("{ 'valid': true, 'invalid_block': null }")
        }
    }
}

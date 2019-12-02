package edu.drexel.se575

import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import kotlin.concurrent.thread

class P2PServer(private var blockchain: BlockChain, private var port: Int) {
    private val connections = ArrayList<ClientHandler>()

    fun listen() {
        val server = ServerSocket(port)
        println("Listening for P2P connections on port: ${this.port}")

        while (true) {
            val client = server.accept()
            println("Peer connected: ${client.inetAddress.hostAddress}")
            val handler = ClientHandler(blockchain, client)
            thread { handler.run() }
            connections.add(handler)
        }
    }

    fun sendTransaction(transaction: Transaction) {
        connections.forEach {
            it.sendTransaction(transaction)
        }
    }
}

class ClientHandler(private val blockchain: BlockChain, private val client: Socket) {
    private val reader: Scanner = Scanner(client.getInputStream())
    private val writer: OutputStream = client.getOutputStream()

    private var running: Boolean = false

    fun run() {
        while (running) {
            try {
                listenForTransactions()
            } catch (ex: Exception) {
                shutdown()
            }
        }
    }

    private fun listenForTransactions() {
        while (running) {
            val transaction = reader.next()
            blockchain.addTransactionToQueue(transactionFromString(blockchain.interpreter.accountList, transaction))
        }
    }

    fun sendTransaction(transaction: Transaction) {
        writer.write(transaction.toString().toByteArray())
    }

    private fun shutdown() {
        running = false
        client.close()
        println("${client.inetAddress.hostAddress} closed the connection.")
    }
}
package edu.drexel.se575

import edu.drexel.se575.data_storage.BlockChain
import java.io.OutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.*
import kotlin.concurrent.thread

class P2PServer(private var blockchain: BlockChain, private var port: Int) {
    fun listen() {
        val server = ServerSocket(port)
        println("Listening for P2P connections on port: ${this.port}")

        while (true) {
            val client = server.accept()
            println("Peer connected: ${client.inetAddress.hostAddress}")
            thread { ClientHandler(blockchain, client).run() }
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
                // Event handling for receiving blockchains from other nodes
                // Periodically send this node's blockchain out to its peers
            } catch (ex: Exception) {
                shutdown()
            }
        }
    }

    private fun shutdown() {
        running = false
        client.close()
        println("${client.inetAddress.hostAddress} closed the connection.")
    }
}
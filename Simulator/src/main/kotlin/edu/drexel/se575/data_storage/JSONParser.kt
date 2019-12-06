package edu.drexel.se575.data_storage

import com.google.gson.*
import edu.drexel.se575.Block
import edu.drexel.se575.BlockChain
import java.io.File
import java.lang.reflect.Type
import java.security.KeyFactory
import java.security.PublicKey
import java.security.spec.RSAPublicKeySpec

val BLOCK_CHAIN_STORAGE_FILE = File("blockChain.txt")

class PublicKeyDeserializer : JsonDeserializer<Any> {

    @Throws(JsonParseException::class)
    override fun deserialize(json: JsonElement, typeOfT: Type, context: JsonDeserializationContext): Any {
        val jsonObject = json.asJsonObject
        return KeyFactory.getInstance("RSA").generatePublic(RSAPublicKeySpec(jsonObject.get("n").asBigInteger, jsonObject.get("e").asBigInteger))
    }
}

fun readBlockChainFromFile(): BlockChain {
    val gsonBuilder = GsonBuilder()
    gsonBuilder.registerTypeAdapter(PublicKey::class.java, PublicKeyDeserializer())
    val gson = gsonBuilder.create()

    val jsonLines = BLOCK_CHAIN_STORAGE_FILE.readLines()
    val blockList: ArrayList<Block> = arrayListOf()
    for (line in jsonLines) {
        blockList.add(gson.fromJson(line, Block::class.java))
    }
    return BlockChain(blockList)
}

fun writeBlockChainToFile(blockChain: BlockChain) {
    var jsonText = ""
    for (block in blockChain.blockList) {
        jsonText += Gson().toJson(block) + "\n"
    }
    BLOCK_CHAIN_STORAGE_FILE.writeText(jsonText)
}




package edu.drexel.se575.data_storage

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import edu.drexel.se575.Block
import edu.drexel.se575.BlockChain
import java.io.File
import java.lang.reflect.Type

val BLOCK_CHAIN_STORAGE_FILE = File("blockChain.txt")
val ARRAY_LIST_OF_BLOCK_TYPE_TOKEN: Type = object : TypeToken<ArrayList<Block>>() {}.type

fun readBlockChainFromFile(): BlockChain {
    val jsonLines = BLOCK_CHAIN_STORAGE_FILE.readLines()
    val blockList: ArrayList<Block> = arrayListOf()
    for (line in jsonLines) {
        blockList.add(Gson().fromJson(line, Block::class.java))
    }

//    val blockList: ArrayList<Block> = Gson().fromJson(jsonText, ARRAY_LIST_OF_BLOCK_TYPE_TOKEN)
    return BlockChain(blockList)
}

fun writeBlockChainToFile(blockChain: BlockChain) {

    var jsonText = ""
    for (block in blockChain.blockList) {
        jsonText += Gson().toJson(block) + "\n"
    }

    BLOCK_CHAIN_STORAGE_FILE.writeText(jsonText)
}




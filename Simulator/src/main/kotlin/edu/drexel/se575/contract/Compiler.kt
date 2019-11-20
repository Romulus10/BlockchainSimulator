package edu.drexel.se575.contract

import java.io.File

/**
 *
 */
class Compiler {

    /**
     *
     */
    private fun processLine(line: String): String {
        var retval = ""
        val intermediate = line.split(' ')
        when (intermediate[0]) {
            "move" -> retval + "0"
            else -> throw InstructionException("An unknown instruction was encountered.")
        }
        return retval
    }

    /**
     *
     */
    private fun readFile(fileName: String): String {
        val list = ArrayList<String>()
        File(fileName).forEachLine {
            list.add(processLine(it))
        }
        val array = arrayOfNulls<Array<String>>(list.size)
        return list.toArray(array).toString()
    }

    /**
     *
     */
    fun compile(fileName: String): String = readFile(fileName)
}
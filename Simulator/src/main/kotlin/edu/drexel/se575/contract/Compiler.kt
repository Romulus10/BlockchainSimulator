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
            "nop" -> {
                retval += "0-"
            }
            "mov" -> {
                retval += "1-"
            }
            "trn" -> {
                retval += "2-"
            }
            "sav" -> {
                retval += "3-"
            }
            "lod" -> {
                retval += "4-"
            }
            "add" -> {
                retval += "5-"
            }
            "sub" -> {
                retval += "6-"
            }
            "mul" -> {
                retval += "7-"
            }
            else -> throw InstructionException("An unknown instruction was encountered.")
        }
        retval += "${intermediate[1]}-"
        retval += "${intermediate[2]}-"
        retval += "${intermediate[3]}|"
        retval = retval.toByteArray().toString()
        return retval
    }

    /**
     *
     */
    private fun readFile(fileName: String): String {
        var retval = ""
        File(fileName).forEachLine {
            retval += processLine(it)
        }
        return retval
    }

    /**
     *
     */
    fun compile(fileName: String): String = readFile(fileName)
}
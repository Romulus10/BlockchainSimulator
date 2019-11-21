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
        if (intermediate[0][0] == '.') {
            retval = intermediate[0]
        } else {
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
                "jlt" -> {
                    retval += "8-"
                }
                "jgt" -> {
                    retval += "9-"
                }
                "jeq" -> {
                    retval += "10-"
                }
                "jmp" -> {
                    retval += "11-"
                }
            }
            retval += "${intermediate[1]}-"
            retval += "${intermediate[2]}-"
            retval += "${intermediate[3]}|"
            retval = retval.toByteArray().toString()
        }
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
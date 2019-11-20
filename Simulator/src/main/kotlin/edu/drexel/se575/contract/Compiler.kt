package edu.drexel.se575.contract

import java.io.File

/**
 *
 */
class Compiler {

    /**
     *
     */
    private fun processLine(line: String): Array<String> = line.split(' ').toTypedArray()

    /**
     *
     */
    private fun readFile(fileName: String): String {
        val list = ArrayList<Array<String>>()
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
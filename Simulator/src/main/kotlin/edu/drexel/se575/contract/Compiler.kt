package edu.drexel.se575.contract

private fun processLine(line: String): String {
    var retval = ""
    val intermediate = line.split(' ')
    if (intermediate[0][0] == '.') {
        retval = intermediate[0]
    } else {
        when (intermediate[0]) {
            "nop" -> {
                retval += "0"
            }
            "mov" -> {
                retval += "1"
            }
            "trn" -> {
                retval += "2"
            }
            "sav" -> {
                retval += "3"
            }
            "lod" -> {
                retval += "4"
            }
            "add" -> {
                retval += "5"
            }
            "sub" -> {
                retval += "6"
            }
            "mul" -> {
                retval += "7"
            }
            "jlt" -> {
                retval += "8"
            }
            "jgt" -> {
                retval += "9"
            }
            "jeq" -> {
                retval += "10"
            }
            "jmp" -> {
                retval += "11"
            }
            "ret" -> {
                retval += "12"
            }
            "act" -> {
                retval += "13"
            }
            "bal" -> {
                retval += "14"
            }
        }
        if (intermediate.size == 2) {
            retval += "-${intermediate[1]}|"
        } else {
            retval += "-${intermediate[1]}-"
            retval += if (intermediate.size == 4) {
                "${intermediate[2]}-${intermediate[3]}|"
            } else {
                "${intermediate[2]}|"
            }
        }
    }
    return retval
}

/**
 *
 */
fun compile(contract: String): String {
    var retval = ""
    contract.split('\n').forEach {
        retval += processLine(it)
    }
    return retval
}
package edu.drexel.se575.contract

/**
 *
 */
class Interpreter {
    // Transient memory values.
    private var memory = IntArray(1024)

    // Persistent memory, accessible between runs.
    private var storage = IntArray(1024) // TODO Find a way to define persistent storage.

    /**
     *
     */
    fun runContract(contract: String) {
        contract.split("|").forEach { instr ->
            instr.split("-").forEach {
                when (it.toInt()) {
                    0 -> {
                    }
                    1 -> {
                    }
                    2 -> {
                    }
                    3 -> {
                    }
                    4 -> {
                    }
                    5 -> {
                    }
                    6 -> {
                    }
                    7 -> {
                    }
                }
            }
        }
    }
}
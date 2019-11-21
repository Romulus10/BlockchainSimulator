package edu.drexel.se575.contract

/**
 *
 */
class Interpreter {
    // Transient memory values.
    private var memory = IntArray(1024)

    // Persistent memory, accessible between runs.
    private var storage = IntArray(1024) // TODO Find a way to define persistent storage.

    private var instructions = HashMap<Int, List<String>>()

    /**
     *
     */
    fun runContract(contract: String) {
        var index = 0
        contract.split("|").forEach { instr ->
            instructions[index++] = instr.split('-')
        }
        index = 0
        while (index < instructions.size) {
            when (instructions[index]?.get(0)) {
                "1" -> { //mov
                    memory[instructions[index]?.get(1)!!.toInt()] = memory[instructions[index]?.get(2)!!.toInt()]
                }
                "2" -> { //trn
                    // TODO Write an account value transfer function.
                }
                "3" -> { //sav
                    storage[instructions[index]?.get(2)!!.toInt()] = memory[instructions[index]?.get(1)!!.toInt()]
                }
                "4" -> { //lod
                    memory[instructions[index]?.get(1)!!.toInt()] = storage[instructions[index]?.get(2)!!.toInt()]
                }
                "5" -> { //add
                    memory[instructions[index]?.get(1)!!.toInt()] += instructions[index]?.get(2)!!.toInt()
                }
                "6" -> { //sub
                    memory[instructions[index]?.get(1)!!.toInt()] -= instructions[index]?.get(2)!!.toInt()
                }
                "7" -> { //mul
                    memory[instructions[index]?.get(1)!!.toInt()] *= instructions[index]?.get(2)!!.toInt()
                }
                "8" -> { //jlt
                    if (memory[instructions[index]?.get(1)!!.toInt()] < memory[instructions[index]?.get(2)!!.toInt()]) {
                        instructions.forEach {
                            if (it.value[0] == instructions[index]?.get(0)) {
                                index = it.key
                            }
                        }
                    }
                }
                "9" -> { //jgt
                    if (memory[instructions[index]?.get(1)!!.toInt()] > memory[instructions[index]?.get(2)!!.toInt()]) {
                        instructions.forEach {
                            if (it.value[0] == instructions[index]?.get(0)) {
                                index = it.key
                            }
                        }
                    }
                }
                "10" -> { //jeq
                    if (memory[instructions[index]?.get(1)!!.toInt()] == memory[instructions[index]?.get(2)!!.toInt()]) {
                        instructions.forEach {
                            if (it.value[0] == instructions[index]?.get(0)) {
                                index = it.key
                            }
                        }
                    }
                }
                "11" -> { //jmp
                    instructions.forEach {
                        if (it.value[0] == instructions[index]?.get(0)) {
                            index = it.key
                        }
                    }
                }
            }
        }
    }
}
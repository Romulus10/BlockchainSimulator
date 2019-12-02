package edu.drexel.se575.contract

import edu.drexel.se575.Account
import edu.drexel.se575.transferAccountValue

/**
 *
 */
class Interpreter {
    // Transient memory values.
    private var memory = IntArray(1024)

    // Persistent memory, accessible between runs.
    private var storage = IntArray(1024) // TODO Find a way to define persistent storage.

    private var instructions = HashMap<Int, List<String>>()

    var accountList = ArrayList<Account>()

    /**
     *
     */
    fun runContract(contract: String): Float {
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
                    transferAccountValue(accountList.toArray().filterIsInstance<Account?>().toTypedArray().apply { if (size != accountList.size) throw Exception() }, instructions[index]?.get(2), instructions[index]?.get(3), instructions[index]?.get(1)!!.toInt())
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
                "12" -> { //ret
                    return memory[instructions[index]?.get(1)!!.toInt()].toFloat()
                }
                "13" -> { //act
                    accountList.add(Account())
                }
                "14" -> { //bal
                    return accountList.filter {
                        it.address == instructions[index]?.get(1)
                    }[0].balance
                }
            }
            index++
        }
        return 0.toFloat()
    }
}

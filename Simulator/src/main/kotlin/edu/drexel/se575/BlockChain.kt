package edu.drexel.se575

import edu.drexel.se575.contract.Interpreter


class BlockChain(var blockList: ArrayList<Block> = arrayListOf()) {

    private var transactionQueue = TransactionQueue(this)
    var interpreter = Interpreter()
    private var stakeManager = StakeManager()

    val size: Int
        get() = blockList.size


    init {
        if (blockList.size == 0) {
            blockList.add(mintStartingBlock())
        }
    }

    private fun payPreviousMinter(){
        if (stakeManager.currentStake != null){
            val accountToPay = stakeManager.currentStake!!.account
            accountToPay.balance += stakeManager.currentStake!!.coinAmountStaked
            accountToPay.balance += blockList.last().transactions.size
        }
    }

    fun addTransactionToQueue(transaction: Transaction) {
        interpreter.runContract(transaction.data)
        transactionQueue.addTransaction(transaction)
        mintBlockIfOverFiveTx()
    }

    fun mintBlockIfOverFiveTx() {
        if (stakeManager.hasNoCoinsStaked()){
            return
        }
        payPreviousMinter()

        val validator = stakeManager.chooseValidator()
        val blockTx = transactionQueue.getTransactionsForBlock()
        val signature = createSignatureForNewBlock(blockTx, validator.privateKey)
        val newBlock = Block(blockTx, validator.publicKey.toString(), signature!!, blockList.last().hash)

        stakeManager.updateCurrentStake(validator)
        blockList.add(newBlock)
    }


    override fun toString(): String {
        return blockList.joinToString(separator = "\n") { it.print() }
    }

    private fun checkBlock(blockInvestigating: Block, previousBlock: Block): Boolean {
        if (previousBlock.hash != blockInvestigating.previousBlockHash) {
            return false
        }
        try {
            blockInvestigating.transactions.forEach {
                interpreter.runContract(it.data)
            }
        } catch (ex: Exception) {
            return false
        }
        return true
    }

    fun isValid(): Boolean {
        for (i in 1 until (blockList.size - 1)) {
            val isValid = checkBlock(blockList[i], blockList[i - 1])
            if (!isValid) {
                return false
            }
        }
        return true
    }

    fun findBrokenBlock(): Int? {
        for (i in 1 until (blockList.size - 1)) {
            val isValid = checkBlock(blockList[i], blockList[i - 1])
            if (!isValid) {
                return i
            }
        }
        return null
    }

    fun messUpBlock(indexToKill: Int) {
        blockList[indexToKill].transactions[0].data = "BAD DATA MUAHAHA"
    }

    fun replaceChain(newBlockChain: BlockChain): Boolean {
        if (newBlockChain.isValid()) {
            this.blockList = newBlockChain.blockList
            return true
        }
        return false
    }

    fun listKnownAddresses(): Array<Account> {
        val accountArray = interpreter.accountList.toArray()
        val finalCastArray = accountArray.filterIsInstance<Account>().toTypedArray()
        if (accountArray.size != finalCastArray.size) {
            throw IllegalArgumentException("Array of known addresses has bad value!")
        }
        return finalCastArray
    }

    fun stakeCoins(account: Account, amount: Float){
        stakeManager.stakeCoins(account, amount)
    }
}
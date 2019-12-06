package edu.drexel.se575

import edu.drexel.se575.contract.Interpreter

const val STAKE_PAYOUT = "Stake Payout"
const val STAKED_COINS = "Staked Coins"


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

    fun listTransactionQueue(): ArrayList<Transaction> {
        return transactionQueue.transactions
    }


    fun addTransactionToQueue(transaction: Transaction) {
        transactionQueue.addTransaction(transaction)
        try {
            transferAccountValue(
                    interpreter.accountList.toArray().filterIsInstance<Account?>().toTypedArray().apply { if (size != interpreter.accountList.size) throw Exception() },
                    transaction.to,
                    transaction.fr,
                    transaction.data.toInt()
            )
        } catch (ex: NumberFormatException) {
            println(ex.toString())
        }
    }

    fun mintBlockIfOverFiveTx() {
        if (stakeManager.hasNoCoinsStaked()) {
            return
        }
        val paymentTransaction = payPreviousMinter()

        val validator = stakeManager.chooseValidator()
        val blockTx = transactionQueue.getTransactionsForBlock()
        val signature = createSignatureForNewBlock(blockTx, validator.privateKey)
        val newBlock = Block(blockTx, validator.publicKey.toString(), signature!!, blockList.last().hash)

        stakeManager.updateCurrentStake(validator)
        blockList.add(newBlock)

        if (paymentTransaction != null) {
            this.addTransactionToQueue(paymentTransaction)
        }
    }


    override fun toString(): String {
        return blockList.joinToString(separator = "\n") { it.print() }
    }

    private fun checkBlock(blockInvestigating: Block, previousBlock: Block): Boolean {
        if (previousBlock.hash != blockInvestigating.previousBlockHash) {
            previousBlock.isValid = false
            return false
        }
        previousBlock.isValid = true
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
        blockList[indexToKill].transactions[0].data =  blockList[indexToKill].transactions[0].data + 1
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

    fun stakeCoins(account: Account, amount: Float) {
        if (account.balance < amount){
            throw IllegalArgumentException("Insufficient account balance for stake!")
        }

        if (amount <= 0 ){
            throw java.lang.IllegalArgumentException("Stake must be greater than 0")
        }

        this.addTransactionToQueue(Transaction(STAKED_COINS, account.address, amount, account.publicKey))
        stakeManager.stakeCoins(account, amount)
    }


    private fun payPreviousMinter(): Transaction? {
        if (stakeManager.currentStake != null) {
            val accountToPay = stakeManager.currentStake!!.account
            val amountToPay = stakeManager.currentStake!!.coinAmountStaked + blockList.last().transactions.size
            accountToPay.balance += amountToPay
            accountToPay.currentStakedCoins -= amountToPay - blockList.last().transactions.size
            return Transaction(accountToPay.address, STAKE_PAYOUT, amountToPay, accountToPay.publicKey)
        }
        return null
    }

}

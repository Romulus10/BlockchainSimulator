package edu.drexel.se575


class StakeManager {
    private var accountStakeAmounts: ArrayList<Stake> = arrayListOf()

    private fun findStake(account: Account): Stake? {
        for (stake in accountStakeAmounts) {
            if (stake.account.publicKey == account.publicKey) {
                return stake
            }
        }
        return null
    }

    fun stakeCoins(account: Account, coins: Float) {
        val currentStake = findStake(account)
        if (currentStake == null) {
            accountStakeAmounts.add(Stake(account, coins))
        } else {
            currentStake.updateStakedCoinAmount(coins)
        }
    }

    fun chooseValidator(): Account {
        val accounts = arrayListOf<Account>()
        val weights = arrayListOf<Float>()
        var weightSum = 0.0

        for (stake in accountStakeAmounts) {
            accounts.add(stake.account)
            weights.add(stake.getWeight())
            weightSum += stake.getWeight()
        }

        var randFloat = Math.random().toFloat() * weightSum

        for (stakeWeight in weights) {
            randFloat -= stakeWeight
            if (randFloat <= 0) {
                return accounts[weights.indexOf(stakeWeight)]
            }
        }
        return accounts[accounts.lastIndex]
    }
}

class Stake(val account: Account, var coinAmountStaked: Float) {
    private var timeCoinsStaked: Long = System.currentTimeMillis()

    fun getWeight(): Float {
        return (System.currentTimeMillis() - timeCoinsStaked.toFloat()) * coinAmountStaked
    }

    fun updateStakedCoinAmount(amount: Float) {
        timeCoinsStaked = System.currentTimeMillis()
        coinAmountStaked = amount
    }

}
package edu.drexel.se575

import java.util.*

/**
 * The backbone of our grand ol' democratic system.
 * @return A single account we're casting our vote for.
 */
fun castVote(accountList: Array<Account>): Account? {
  val voteValues = arrayOfNulls<Double>(accountList.size)
  val rand = Random()
  for (x in accountList.indices) {
    voteValues[x] = (accountList[x].weight / 1000 * rand.nextInt(100)) * 1.0
  }
  return maxAccountWeight(accountList, voteValues)
}

/**
 * Here's where we hope we find out the protocol is paying us to sit here.
 * @return Was the elected creator one of the accounts we control?
 */
fun validateVote(): Boolean {
  // Query the network for the full list of votes
  return false
}

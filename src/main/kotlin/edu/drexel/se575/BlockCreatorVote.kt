package edu.drexel.se575

/**
 * The backbone of our grand ol' democratic system.
 * @return A single account we're casting our vote for.
 */
fun castVote(accountList: Array<Account>): Account {
  var vote_values = arrayOfNulls<Account>(accountList.size)
  var rand = Random()
  for (x in accountList.indices) {
    vote_values[x] = accountList[x].weight/1000 * rand.nextDouble(1)
  }
  return max_account_weight(vote_values)
}

/**
 * Here's where we hope we find out the protocol is paying us to sit here.
 * @return Was the elected creator one of the accounts we control?
 */
fun validateVote(): Boolean {
  // Query the network for the full list of votes
  return false
}

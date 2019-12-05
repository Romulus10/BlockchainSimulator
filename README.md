# BlockchainSimulator
A blockchain simulator for SE 575

Open two terminal windows. In one, run the Simulator build, and in the other run the Frontend installation process. Once both are operational, navigate a web browser to [http://localhost:8100](http://localhost:8100).

## Simulator
### Build
```
cd Simulator
./gradlew run
```
(If both JRE and JDK are installed, gradle may use java from the JRE. Ensure JAVA_HOME is set to path of JDK.)

## Frontend
### Installation
1. Install node.js if you have not. https://nodejs.org/en/download/
2. `npm install -g ionic`
3. `cd Frontend/ionic-app`
4. `npm install`
5. `ionic serve`
6. Website will be hosted on `localhost:8100`

# Smart Contract Language
In order to carry out general computing and value-moving requirements, including the proof-of-stake algorithm, the blockchain simulator will include a simple smart contract system. The smart contracts will provide functions for simple, proof-of-concept programming structures, and will allow for transferring tokens between accounts.

## Syntax
The smart contract language for this blockchain will be a fairly easily-assembled low-level language that will carry instructions for calculations in the following grammar:

```
.<label>
<instr>
<instr> <arg>
<instr> <arg> <arg>
<instr> <arg> <arg> <arg>
```

## Language Reference
| Code  | Instruction   | Argument                                   | Description                                  |
| ----- |:-------------:| :----------------------------------------: | -------------------------------------------: |
| 0     | nop           | None                                       | No operation.                                |
| 1     | mov           | 'to' memory address, 'from' memory address | Move memory values.                          |
| 2     | trn           | token volume, 'to' account, 'from' account | Transfer tokens from one account to another. |
| 3     | sav           | memory address, storage address            | Save a value to permanent storage.           |
| 4     | lod           | memory address, storage address            | Load a value from permanent storage.         |
| 5     | add           | address, value                             | Add a value to a given memory location.      |
| 6     | sub           | address, value                             | Subtract from a given memory location.       |
| 7     | mul           | address, value                             | Multiply a given memory location by a value. |
| 8     | jlt           | x, y, label                                | Jump to label if x is less than y.           |
| 9     | jgt           | x, y, label                                | Jump to a label if x is greater than y.      |
| 10    | jeq           | x, y, label                                | Jump to a label if x is equal to y.          |
| 11    | jmp           | label                                      | Jump unconditionally to a label.             |
| 12    | ret           | x                                          | Returns the value in memory address x.       |
| 13    | act           | None                                       | Creates a new account on the chain.          |
| 14    | bal           | acct                                       | Retrieve a balance given an account name.    |

## Examples
### Basic Math
```
add 0 1
add 0 1
ret 0
```
Returns `2`.

```
add 0 10
loop:
add 1 1
jlt 1 0 loop
ret 1
```
Sets memory index 0 to 10, then adds 1 to memory index 1 repeatedly until it contains 10. Returns the value of memory index 1.

package com.github.mcsim415.tictactoe

class TicTacToe {
    private val player = Cell.O
    private var turn = player
    private val diff = 4
    private val board = Board(diff)
    private val ai = AI(board, player.opposite())

    fun start() {
        println("Welcome to Tic Tac Toe!")
        println("Player is X, AI is O")
        while (!board.isFull() && !board.hasWinner()) {
            turn(turn)
        }
    }

    private fun turn(who: Cell): Boolean {
        println(board.print())
        return if (who == player) {
            println("Player turn.")
            println("Please enter cord like '1 1' ~ '${diff} ${diff}'.")
            val cord = readLine()!!
            val split = cord.split(' ')
            if (split.size == 2) {
                val x = split[1].toInt() - 1
                val y = split[0].toInt() - 1
                if (x in 0 until diff && y in 0 until diff) {
                    if (board.isEmpty(x, y)) {
                        board.set(x, y, player)
                        turn = player.opposite()
                        true
                    } else {
                        println("This cell is not empty.")
                        false
                    }
                } else {
                    println("Please enter cord like '1 1' ~ '${diff} ${diff}'.")
                    false
                }
            } else {
                println("Invalid input.")
                false
            }
        } else {
            println("AI turn. Calculating..")
            val result = ai.calculate()
            board.set(result, turn)
            turn = turn.opposite()
            true
        }
    }
}

fun main() {
    TicTacToe().start()
}
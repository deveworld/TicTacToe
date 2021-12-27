package com.github.mcsim415.tictactoe

import kotlin.math.pow

class AI(private val board: Board, private val marker: Cell) {
    private val preMove = mutableListOf(arrayListOf<Int>())
    private var bestMove = arrayListOf<Int>()

    @Suppress("UNCHECKED_CAST")
    fun calculate(): ArrayList<Int> {
        preMove.clear()
        bestMove.clear()
        minimaxAB(board, 0, Float.NEGATIVE_INFINITY, Float.POSITIVE_INFINITY, true)
        for (move in preMove) {
            println("score: ${move[2]} , x: ${move[0]+1} , y: ${move[1]+1}")
        }
        return bestMove
    }

    private fun minimaxAB(board: Board, depth: Int, alpha: Float, beta: Float, aiTurn: Boolean): Any {
        var newAlpha = alpha
        var newBeta = beta
        if (board.isFull() || board.hasWinner()) {
            return when (board.whoWin()) {
                marker -> ((board.size.toFloat().pow(2)+1) - depth).toInt()
                marker.opposite() -> ((-board.size.toFloat().pow(2)-1) + depth).toInt()
                else -> 0
            }
        }

        val moves = board.getAvailableMoves()
        if (aiTurn) {
            var bestScore = Float.NEGATIVE_INFINITY
            for (move in moves) {
                board.set(move, marker)
                val score = (minimaxAB(board, depth + 1, newAlpha, newBeta, false) as Int).toFloat()
                board.set(move, Cell.EMPTY)
                if (score > bestScore) {
                    bestScore = score
                    if (depth == 0) {
                        bestMove = move
                    }
                }
                newAlpha = max(newAlpha, score)
                if (depth == 0) {
                    move.add(score.toInt())
                    preMove.add(move)
                }
                if (newBeta <= newAlpha) {
                    return (board.size.toFloat().pow(2)).toInt()
                }
            }
            return if (depth == 0) {
                preMove
            } else {
                bestScore.toInt()
            }
        } else {
            var bestScore = Float.POSITIVE_INFINITY
            for (move in moves) {
                board.set(move, marker.opposite())
                val score = (minimaxAB(board, depth + 1, newAlpha, newBeta, true) as Int).toFloat()
                board.set(move, Cell.EMPTY)
                bestScore = min(bestScore, score)
                newBeta = min(newBeta, score)
                if (newBeta <= newAlpha) {
                    return (-board.size.toFloat().pow(2)).toInt()
                }
            }
            return bestScore.toInt()
        }
    }

    private fun min(a: Any, b: Any): Float {
        if (a is Float && b is Float) {
            return if (a < b) a else b
        }
        throw IllegalArgumentException("min() requires two floats")
    }

    private fun max(a: Any, b: Any): Float {
        if (a is Float && b is Float) {
            return if (a > b) a else b
        }
        throw IllegalArgumentException("max() requires two floats")
    }
}

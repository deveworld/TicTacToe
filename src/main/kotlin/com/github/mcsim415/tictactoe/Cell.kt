package com.github.mcsim415.tictactoe

class Cell(val state: Int) {
    fun opposite(): Cell {
        return when (this) {
            X -> {
                O
            }
            O -> {
                X
            }
            else -> {
                EMPTY
            }
        }
    }

    override fun toString(): String {
        return when (this) {
            X -> {
                " X "
            }
            O -> {
                " O "
            }
            else -> {
                " | "
            }
        }
    }

    companion object {
        val EMPTY = Cell(0)
        val X = Cell(1)
        val O = Cell(2)
    }
}

package com.github.mcsim415.tictactoe

data class Board(val size: Int, val board: Array<Array<Cell>>) {
    constructor(size: Int) : this(size, Array(size) { Array(size) { Cell.EMPTY } })

    fun print(): String {
        val sb = StringBuilder()
        for (i in 0 until size) {
            for (j in 0 until size) {
                sb.append(get(i, j))
            }
            sb.append("\n")
        }
        return sb.toString()
    }

    fun get(x: Int, y: Int): Cell {
        return board[x][y]
    }

    fun getAvailableMoves(): ArrayList<ArrayList<Int>> {
        val availableMoves = arrayListOf(arrayListOf<Int>())
        availableMoves.clear()
        for (x in 0 until size) {
            for (y in 0 until size) {
                if (board[x][y] == Cell.EMPTY) {
                    availableMoves.add(arrayListOf(x, y))
                }
            }
        }
        return availableMoves
    }

    fun set(cord: ArrayList<Int>, cell: Cell) {
        set(cord[0], cord[1], cell)
    }

    fun set(x: Int, y: Int, cell: Cell) {
        board[x][y] = cell
    }

    fun isEmpty(x: Int, y: Int): Boolean {
        return get(x, y) == Cell.EMPTY
    }

    fun isFull(): Boolean {
        for (x in 0 until size) {
            for (y in 0 until size) {
                if (isEmpty(x, y)) {
                    return false
                }
            }
        }
        return true
    }

    fun hasWinner(): Boolean {
        return isWin(Cell.X) || isWin(Cell.O)
    }

    fun whoWin(): Cell {
        return if (isWin(Cell.X)) {
            Cell.X
        } else if (isWin(Cell.O)) {
            Cell.O
        } else {
            Cell.EMPTY
        }
    }

    private fun isWin(player: Cell): Boolean {
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (get(i, j) !== player) break
                if (j == size - 1) {
                    return true
                }
            }
        }
        for (i in 0 until size) {
            for (j in 0 until size) {
                if (get(j, i) !== player) break
                if (j == size - 1) {
                    return true
                }
            }
        }
        for (i in 0 until size) {
            if (get(i, i) !== player) break
            if (i == size - 1) {
                return true
            }
        }
        for (i in 0 until size) {
            if (get(i, size - 1 - i) !== player) break
            if (i == size - 1) {
                return true
            }
        }
        return false
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Board

        if (size != other.size) return false
        if (!board.contentDeepEquals(other.board)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = size
        result = 31 * result + board.contentDeepHashCode()
        return result
    }
}

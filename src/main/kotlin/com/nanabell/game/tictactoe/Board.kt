package com.nanabell.game.tictactoe

import kotlin.reflect.KProperty

class Board(size: Int) {

    private val _board = Array(size) { v -> Array(size) { h -> Box(v, h) } }

    fun claimBox(x: Int, y: Int, owner: Player): Boolean {
        return _board[x][y].setOccupied(owner)
    }

    fun allOccupied(): Boolean {
        return _board.flatten().all { it.isOccupied() }
    }


    fun checkWinner(lastY: Int, lastX: Int): Player? {
        return checkBox(_board[lastY][lastX])
    }

    private fun checkBox(box: Box): Player? {
        var winner: Player? = null
        val targetPlayer = box.occupiedBy ?: return winner

        // Horizontal
        for (x in _board.indices) {
            val testBox = _board[x][box.yPos]

            if (testBox.occupiedBy != targetPlayer) {
                winner = null
                break
            }

            winner = box.occupiedBy
        }
        if (winner != null) return winner

        // Vertical
        for (y in _board.indices) {
            val testBox = _board[box.xPos][y]

            if (testBox.occupiedBy != targetPlayer) {
                winner = null
                break
            }

            winner = box.occupiedBy
        }
        if (winner != null) return winner


        // Top Left to Bottom Right
        var pos = 0
        while (pos < _board.size) {
            val testBox = _board[pos][pos]

            if (testBox.occupiedBy != targetPlayer) {
                winner = null
                break
            }

            winner = box.occupiedBy
            pos++
        }
        if (winner != null) return winner


        // Top Right to bottom Left
        var x = _board.size - 1
        var y = 0
        while (x >= 0 && y < _board.size) {
            val testBox = _board[x][y]

            if (testBox.occupiedBy != targetPlayer) {
                winner = null
                break
            }

            winner = box.occupiedBy
            x--
            y++
        }

        return winner
    }
}

class Box(val xPos: Int, val yPos: Int) {

    private var state = STATE.FREE
    var occupiedBy: Player? = null
        private set

    fun setOccupied(owner: Player): Boolean {
        if (state != STATE.FREE) {
            return false
        }

        state = STATE.OCCUPIED
        occupiedBy = owner

        return true
    }

    fun isOccupied() = state == STATE.OCCUPIED

}

enum class STATE {
    FREE,
    OCCUPIED,
}
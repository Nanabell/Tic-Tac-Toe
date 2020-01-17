package com.nanabell.game.tictactoe

import tornadofx.Component
import tornadofx.ScopedInstance

class Game : Component(), ScopedInstance {

    val boardSize = 3

    private val board = Board(boardSize)

    private val players = listOf(Player("O"), Player("X"))
    private val sequence = players.asSequence().repeat().iterator()

    var activePlayer = sequence.next()
        private set

    var gameState: GameState = GameState.RUNNING
        private set

    var winner: Player? = null
        private set

    fun claim(x: Int, y: Int): ClaimResponse {
        if (gameState != GameState.RUNNING) ClaimResponse(ClaimStatus.FAILURE, null) // dont do anything if game is not running

        val claimed = board.claimBox(x, y, activePlayer)
        val response = ClaimResponse(if (claimed) ClaimStatus.SUCCESS else ClaimStatus.FAILURE, activePlayer)

        val winner = checkPlayer(x, y)
        if (winner != null) {
            gameState = GameState.FINISHED
            this.winner = winner

            return response // no need to switch players again
        }

        if (claimed)
            switchPlayer()

        if (board.allOccupied())
            gameState = GameState.DRAW

        return response
    }

    private fun checkPlayer(x: Int, y: Int): Player? {
        return board.checkWinner(x, y)
    }

    private fun switchPlayer() {
        activePlayer = sequence.next()
    }
}

enum class GameState {
    RUNNING,
    FINISHED,
    DRAW
}

fun <T> Sequence<T>.repeat() = sequence { while (true) yieldAll(this@repeat) }
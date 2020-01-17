package com.nanabell.game.tictactoe


data class ClaimResponse(val status: ClaimStatus, val owner: Player?)

enum class ClaimStatus {
    SUCCESS,
    FAILURE
}
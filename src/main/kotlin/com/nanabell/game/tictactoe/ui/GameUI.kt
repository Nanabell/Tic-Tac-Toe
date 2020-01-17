package com.nanabell.game.tictactoe.ui

import com.nanabell.game.tictactoe.ClaimStatus
import com.nanabell.game.tictactoe.Game
import com.nanabell.game.tictactoe.GameState
import javafx.geometry.Pos
import javafx.scene.control.Alert
import javafx.scene.control.ButtonType
import javafx.scene.control.Label
import javafx.scene.layout.Priority
import javafx.scene.text.Font
import javafx.scene.text.TextAlignment
import tornadofx.*

class GameUI : View() {

    val gameTitle: String by param()

    private val game: Game by inject()

    override val root = vbox {
        title = gameTitle

        minWidth = 500.0
        minHeight = 500.0


        gridpane {
            isGridLinesVisible = true
            hgrow = Priority.ALWAYS
            vgrow = Priority.ALWAYS

            for (xIndex in 0 until game.boardSize) {
                for (yIndex in 0 until game.boardSize) {
                    borderpane {
                        id = "borderpane-${yIndex}_$xIndex"

                        center = label {
                            id = "label_${yIndex}_$xIndex"

                            useMaxWidth = true
                            useMaxHeight = true

                            alignment = Pos.CENTER
                            textAlignment = TextAlignment.CENTER
                            font = Font.font(50.0)

                        }

                        gridpaneConstraints {
                            columnRowIndex(yIndex, xIndex)
                        }
                    }
                }
            }


            for (index in 0 until game.boardSize) {
                constraintsForRow(index).percentHeight = 100.0 / game.boardSize
                constraintsForColumn(index).percentWidth = 100.0 / game.boardSize
            }

            setOnMouseClicked {
                if (game.gameState != GameState.RUNNING) // Do nothing if game is no longer running
                    return@setOnMouseClicked

                if (it.target is Label) {
                    val label = it.target as Label

                    val indexes = label.id.split("_").takeLast(2)
                    val response = game.claim(indexes.first().toInt(), indexes.last().toInt())

                    if (response.status == ClaimStatus.SUCCESS)
                        label.text = response.owner?.name
                }

                if (game.gameState == GameState.FINISHED || game.gameState == GameState.DRAW) {
                    alert(
                        Alert.AlertType.INFORMATION,
                        "Game Finished!",
                         if (game.gameState == GameState.FINISHED) "Player ${game.winner?.name} has won!" else "Game has ended in a Draw!",
                        ButtonType.FINISH,
                        title = "Game Finished!"
                    )
                }
            }
        }
    }
}

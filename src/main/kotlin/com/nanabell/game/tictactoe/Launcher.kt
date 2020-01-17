package com.nanabell.game.tictactoe

import com.nanabell.game.tictactoe.ui.GameUI
import javafx.scene.Scene
import org.scenicview.ScenicView
import tornadofx.App
import tornadofx.UIComponent
import tornadofx.find

class Launcher : App(GameUI::class) {

    init {
        find(GameUI::class, scope, GameUI::gameTitle to "Tic Tac Toe")
    }

/*    override fun onBeforeShow(view: UIComponent) {
        ScenicView.show(view.root)
    }*/
}
package edu.austral.dissis.chess.components

import com.google.gson.Gson
import edu.austral.dissis.chess.gui.GameEventListener
import edu.austral.dissis.chess.gui.Move
import edu.austral.ingsis.clientserver.Message

class MovementListener : GameEventListener {
    override fun handleMove(move: Move) {
        val gson = Gson()
        val request = Util.requestToJson(move)
        val data = gson.toJson(request)
        SimpleClient.send(Message("movement", data))
    }

    override fun handleRedo() {
        SimpleClient.send(Message("state", "redo"))
    }

    override fun handleUndo() {
        SimpleClient.send(Message("state", "undo"))
    }
}

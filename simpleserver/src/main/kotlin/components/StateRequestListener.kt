package edu.austral.dissis.chess.components

import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class StateRequestListener : MessageListener<String> {
    override fun handleMessage(message: Message<String>) {
        if (message.payload == "undo") {
            if (SimpleServer.state == null) {
                SimpleServer.state = SimpleServer.game.states.last()
            } else {
                SimpleServer.state = SimpleServer.state!!.states.lastOrNull() ?: SimpleServer.state
            }
        }

        if (message.payload == "redo") {
            val index = SimpleServer.game.states.indexOf(SimpleServer.state)
            if (index != -1 && index < SimpleServer.game.states.size - 1) {
                SimpleServer.state = SimpleServer.game.states[index + 1]
            } else {
                SimpleServer.state = null
            }
        }

        SimpleServer.notifyListeners()
    }
}

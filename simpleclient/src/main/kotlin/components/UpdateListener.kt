package edu.austral.dissis.chess.components

import edu.austral.dissis.chess.gui.InvalidMove
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class UpdateListener : MessageListener<String> {
    override fun handleMessage(message: Message<String>) {
        SimpleClient.root!!.handleMoveResult(InvalidMove(message.payload))
    }
}

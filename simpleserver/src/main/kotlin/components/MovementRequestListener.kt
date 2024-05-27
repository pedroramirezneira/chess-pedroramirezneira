package edu.austral.dissis.chess.components

import com.google.gson.Gson
import edu.austral.dissis.chess.models.MovementRequest
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener

class MovementRequestListener : MessageListener<String> {
    override fun handleMessage(message: Message<String>) {
        val gson = Gson()
        val request = gson.fromJson(message.payload, MovementRequest::class.java)
        val coordinates = request.toCoordinates()
        val game = SimpleServer.state ?: SimpleServer.game
        val state = game.moveFrom { coordinates }
        when (state) {
            SimpleServer.state ?: SimpleServer.game -> SimpleServer.broadcast(Message("update", "Invalid move."))
            else -> {
                SimpleServer.game = state
                SimpleServer.state = null
                SimpleServer.notifyListeners()
            }
        }
    }
}

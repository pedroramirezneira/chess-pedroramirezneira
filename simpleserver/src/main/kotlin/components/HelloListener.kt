package edu.austral.dissis.chess.components

import com.google.gson.Gson
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.ServerConnectionListener

class HelloListener : ServerConnectionListener {
    override fun handleClientConnection(clientId: String) {
        val gson = Gson()
        val data = Util.gameToJson(SimpleServer.game)
        val json = gson.toJson(data)
        SimpleServer.sendMessage(clientId, Message("board", json))
    }

    override fun handleClientConnectionClosed(clientId: String) {
    }
}

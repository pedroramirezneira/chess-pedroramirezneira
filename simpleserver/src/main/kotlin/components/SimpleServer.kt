package edu.austral.dissis.chess.components

import com.fasterxml.jackson.core.type.TypeReference
import com.google.gson.Gson
import edu.austral.dissis.chess.engine.components.Game
import edu.austral.dissis.chess.engine.components.GameEnded
import edu.austral.dissis.chess.engine.engine.components.movements.Castling
import edu.austral.dissis.chess.engine.engine.components.movements.EnPassant
import edu.austral.dissis.chess.engine.engine.components.movements.Promotion
import edu.austral.dissis.chess.engine.engine.components.validations.Check
import edu.austral.dissis.chess.engine.engine.components.winconditions.CheckMate
import edu.austral.dissis.chess.engine.interfaces.IGame
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.Server
import edu.austral.ingsis.clientserver.netty.server.NettyServerBuilder

object SimpleServer : Server {
    private val reference = object : TypeReference<Message<String>>() {}

    private val server =
        NettyServerBuilder.Companion.createDefault()
            .withPort(8095)
            .withConnectionListener(HelloListener())
            .addMessageListener("movement", reference, MovementRequestListener())
            .addMessageListener("state", reference, StateRequestListener())
            .build()

    private val resource = {}.javaClass.getResourceAsStream("/config/config.json")!!

    private val config = resource.readAllBytes()!!.toString(Charsets.UTF_8)

    var game: IGame =
        Game fromJson config changeRules {
            add movement Castling()
            add movement Promotion()
            add movement EnPassant()
            add validation Check()
            add winCondition CheckMate()
        }

    var state: IGame? = null

    fun notifyListeners() {
        val gson = Gson()
        val data = Util.gameToJson(game)
        val json = gson.toJson(data)
        when {
            game is GameEnded -> {
                val winner = if (game.currentPlayer) "white" else "black"
                val endData = data.plus("ended" to true).plus("winner" to winner)
                val endJson = gson.toJson(endData)
                broadcast(Message("board", endJson))
            }

            state != null -> {
                val previousData = Util.gameToJson(state!!)
                val previousJson = gson.toJson(previousData)
                broadcast(Message("board", previousJson))
            }

            else -> broadcast(Message("board", json))
        }
    }

    override fun <P : Any> broadcast(message: Message<P>) {
        server.broadcast(message)
    }

    override fun <P : Any> sendMessage(
        clientId: String,
        message: Message<P>,
    ) {
        server.sendMessage(clientId, message)
    }

    override fun start() {
        server.start()
    }

    override fun stop() {
        server.stop()
    }
}

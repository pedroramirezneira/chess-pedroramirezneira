package edu.austral.dissis.chess.components

import com.fasterxml.jackson.core.type.TypeReference
import edu.austral.dissis.chess.gui.BoardSize
import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.chess.gui.GameView
import edu.austral.ingsis.clientserver.Client
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.netty.client.NettyClientBuilder
import java.net.InetSocketAddress

object SimpleClient : Client {
    private val reference = object : TypeReference<Message<String>>() {}

    private val client =
        NettyClientBuilder.Companion.createDefault()
            .withAddress(InetSocketAddress("localhost", 8095))
            .addMessageListener("board", reference, BoardListener())
            .addMessageListener("update", reference, UpdateListener())
            .build()

    var root: GameView? = null

    var size: BoardSize? = null

    var player: Boolean? = null

    var pieces: List<ChessPiece>? = null

    override fun closeConnection() {
        return client.closeConnection()
    }

    override fun connect() {
        return client.connect()
    }

    override fun <P : Any> send(message: Message<P>) {
        return client.send(message)
    }
}

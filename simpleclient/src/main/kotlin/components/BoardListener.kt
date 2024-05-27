package edu.austral.dissis.chess.components

import com.google.gson.Gson
import edu.austral.dissis.chess.gui.BoardSize
import edu.austral.dissis.chess.gui.ChessPiece
import edu.austral.dissis.chess.gui.GameOver
import edu.austral.dissis.chess.gui.InitialState
import edu.austral.dissis.chess.gui.NewGameState
import edu.austral.dissis.chess.gui.PlayerColor
import edu.austral.dissis.chess.gui.Position
import edu.austral.dissis.chess.gui.UndoState
import edu.austral.dissis.chess.models.GameData
import edu.austral.ingsis.clientserver.Message
import edu.austral.ingsis.clientserver.MessageListener
import javafx.application.Platform

class BoardListener : MessageListener<String> {
    override fun handleMessage(message: Message<String>) {
        val gson = Gson()
        val data = gson.fromJson(message.payload, GameData::class.java)

        val pieces =
            data.board.pieces.map { tile ->
                val id = System.identityHashCode(tile.piece).toString()
                val color =
                    when {
                        tile.piece.color -> PlayerColor.WHITE
                        else -> PlayerColor.BLACK
                    }
                val position = Position(data.board.height - tile.coordinate.y, tile.coordinate.x + 1)
                val oldPiece =
                    SimpleClient.pieces?.find { piece ->
                        piece.position == position && piece.color == color && piece.pieceId == tile.piece.type
                    }
                oldPiece ?: ChessPiece(id, color, position, tile.piece.type)
            }

        SimpleClient.pieces = pieces
        SimpleClient.size = BoardSize(data.board.width, data.board.height)
        val playerColor = if (data.player) PlayerColor.WHITE else PlayerColor.BLACK

        if (data.ended == true) {
            val result =
                when {
                    data.winner == "white" -> GameOver(PlayerColor.WHITE)
                    else -> GameOver(PlayerColor.BLACK)
                }

            Platform.runLater {
                SimpleClient.root!!.handleMoveResult(result)
            }
            return
        }

        if (SimpleClient.player == null) {
            SimpleClient.player = data.player
            val initialState = InitialState(SimpleClient.size!!, pieces, playerColor)

            Platform.runLater {
                SimpleClient.root!!.handleInitialState(initialState)
            }
            return
        }

        val state = NewGameState(pieces, playerColor, UndoState(canUndo = true, canRedo = true))
        SimpleClient.player = data.player

        Platform.runLater {
            SimpleClient.root!!.handleMoveResult(state)
        }
    }
}

package edu.austral.dissis.chess.engine.components.winconditions

import edu.austral.dissis.chess.engine.interfaces.WinCondition

val noPiecesLeft = WinCondition { game -> game.board.getPieces().none() { it.piece.color != game.currentPlayer } }
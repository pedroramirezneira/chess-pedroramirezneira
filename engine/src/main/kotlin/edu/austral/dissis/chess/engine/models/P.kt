package edu.austral.dissis.chess.engine.models

import edu.austral.dissis.chess.engine.interfaces.Coordinate

data class P(override val x: Int, override val y: Int) : Coordinate

package edu.austral.dissis.chess.engine.models

import com.google.gson.annotations.SerializedName

data class GameData(
    val board: BoardData,
    val pieces: List<PieceData>?,
)

data class BoardData(
    val height: Int,
    val width: Int,
    @SerializedName("white_color") val whiteColor: String,
    @SerializedName("black_color") val blackColor: String,
    val mirror: Boolean?,
    @SerializedName("white_arrangement") val whiteArrangement: List<List<String>>,
    @SerializedName("black_arrangement") val blackArrangement: List<List<String>>?,
)

data class PieceData(
    val type: String,
    @SerializedName("initial_distance") val initialDistance: Int?,
    @SerializedName("movement_distance") val movementDistance: Int?,
    val ghost: Boolean?,
    @SerializedName("movement_vectors") val movementVectors: List<String>,
    @SerializedName("attack_vectors") val attackVectors: List<String>?,
)

package com.mediaversetv.chess.components

import com.typesafe.config.ConfigFactory
import java.io.File

object Configuration {
    private val config = ConfigFactory.parseFile(File("application.conf"))

    val httpPort = try {
        config.getInt("http")
    }
    catch (e: Exception) {
        null
    }

    val httpsPort = try {
        config.getInt("https")
    } catch (e: Exception) {
        null
    }
}

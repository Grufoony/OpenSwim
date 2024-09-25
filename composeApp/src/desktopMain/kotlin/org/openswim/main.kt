package org.openswim

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

import Athletes
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "OpenSwim",
    ) {
        Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")

        transaction {
            SchemaUtils.create(Athletes)

            val athleteId = Athletes.insert {
                it[name] = "Mario"
                it[surname] = "Rossi"
                it[sex] = "M"
            } get Athletes.id

            val secondAthleteId = Athletes.insert {
                it[name] = "Maria"
                it[surname] = "Bianchi"
                it[sex] = "F"
            } get Athletes.id

            println("Created new athletes with ids $athleteId and $secondAthleteId.")
        }
        App()
    }
}
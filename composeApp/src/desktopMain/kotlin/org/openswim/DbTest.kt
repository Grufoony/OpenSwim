package org.openswim

import java.io.File
import java.sql.DriverManager
import kotlin.collections.ArrayList

fun main(args: Array<String>) {

    val conn = DriverManager.getConnection("jdbc:sqlite:openswim.sqlite")

    conn.autoCommit = false

    val stmts = File ("composeApp/src/desktopMain/kotlin/org/openswim/database/model/db_test.sql").readText().split(";").toCollection(ArrayList<String>())
    stmts.forEach {
        it.trim('\n', ' ', '\t', '\r')
    }
    stmts.removeLast()

    conn.createStatement().use { stmt ->
        stmts.forEach {
            println("Executing: $it")
            try {
                stmt.executeUpdate(it)
            } catch (e: Exception) {
                println(e.message)
                conn.rollback()
                return
            }
        }
    }

    conn.commit()


}
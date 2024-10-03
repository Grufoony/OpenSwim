package org.openswim.database.dao

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.VarCharColumnType

object Teams : Table("teams") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", VarCharColumnType().colLength)

}
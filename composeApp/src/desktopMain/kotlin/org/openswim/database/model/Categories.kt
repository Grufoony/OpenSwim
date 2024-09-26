package org.openswim.database.model

import org.jetbrains.exposed.sql.SqlExpressionBuilder.isNotNull
import org.jetbrains.exposed.sql.Table

object Categories : Table("category") {
    val id = integer("id").autoIncrement()
    val code = varchar("code", 10)
    val name = varchar("name", 50)
    val sex = varchar("sex", 1).check { it inList listOf("M", "F") }
    val minAge = integer("min_age")
    val maxAge = integer("max_age")
    val minYear = integer("min_year")
    val maxYear = integer("max_year")

    val custom = bool("custom").default(false)

    override val primaryKey = PrimaryKey(id)
}
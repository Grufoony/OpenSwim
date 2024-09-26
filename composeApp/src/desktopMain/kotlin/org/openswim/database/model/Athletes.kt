package org.openswim.database.model

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.VarCharColumnType
import org.jetbrains.exposed.sql.javatime.date

object Athletes : Table("athletes") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", VarCharColumnType().colLength)
    val surname = varchar("surname", VarCharColumnType().colLength)
    val birthDate = date("birth_date")
    val sex = varchar("sex", 1).check { it inList listOf("M", "F") }

    val category = reference(
        "category",
        refColumn = Categories.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val teamId = reference(
        "team_id",
        refColumn = Teams.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )

    override val primaryKey = PrimaryKey(id)
}
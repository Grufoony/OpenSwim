package org.openswim.database.model

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.VarCharColumnType

object Teams : Table("teams") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", VarCharColumnType().colLength)
    val category = reference(
        "category",
        refColumn = Categories.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
}
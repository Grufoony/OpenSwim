package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Committees : IntIdTable("committees") {
    val name = varchar("name", 255)
}

class Committee(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Committee>(Committees)

    var name by Committees.name
}
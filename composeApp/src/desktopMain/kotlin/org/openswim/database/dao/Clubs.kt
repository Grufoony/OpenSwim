package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Clubs : IntIdTable("clubs") {
    val name = varchar("name", 255)
}

class Club(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Club>(Clubs)

    var name by Clubs.name
}
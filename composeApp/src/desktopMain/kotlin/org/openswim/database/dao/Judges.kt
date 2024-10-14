package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Judges : IntIdTable("judges") {
    val name = varchar("name", 255)
    val surname = varchar("surname", 255)
}

class Judge(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Judge>(Judges)

    var name by Judges.name
    var surname by Judges.surname
}
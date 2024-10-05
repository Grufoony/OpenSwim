package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object RelayTypes : IntIdTable("relay_types") {
    val name = varchar("name", 255)
}

class RelayType(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RelayType>(RelayTypes)

    var name by RelayTypes.name
}
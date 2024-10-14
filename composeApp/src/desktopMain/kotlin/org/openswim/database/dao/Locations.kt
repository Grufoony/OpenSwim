package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Locations : IntIdTable("locations") {
    val name = varchar("name", 255)
    val addressId = reference("address_id", Addresses).nullable()
    val lanes = integer("lanes")
    val lanesLength = integer("lanes_length")

}

class Location(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Location>(Locations)

    var name by Locations.name
    var address by Address optionalReferencedOn Locations.addressId
    var lanes by Locations.lanes
    var lanesLength by Locations.lanesLength
}
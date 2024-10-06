package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Relays : IntIdTable("relays") {
    val name = varchar("name", 255)
    val category_id =
        reference("category_id", Categories, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val relay_type_id =
        reference("relay_type_id", RelayTypes, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
}

class Relay(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Relay>(Relays)

    var name by Relays.name
    var category by Category referencedOn Relays.category_id
    var relayType by RelayType referencedOn Relays.relay_type_id
    var teams by RelayTeam via RelaySubscriptions
    var subscription by RelaySubscription via RelaySubscriptions

}
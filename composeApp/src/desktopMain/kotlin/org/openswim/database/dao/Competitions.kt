package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.javatime.date

object Competitions : IntIdTable("competitions") {
    val name = varchar("name", 255)
    val startDate = date("start_date")
    val endDate = date("end_date")
    val committeeId = reference("committee_id", Committees).nullable()
    val chronosId = reference("chronos_id", Chronos).nullable()
    val locationId = reference("location_id", Locations).nullable()
}

class Competition(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Competition>(Competitions)

    var name by Competitions.name
    var startDate by Competitions.startDate
    var endDate by Competitions.endDate
    var committee by Committee optionalReferencedOn Competitions.committeeId
    var chronos by Chrono optionalReferencedOn Competitions.chronosId
    var location by Location optionalReferencedOn Competitions.locationId

}
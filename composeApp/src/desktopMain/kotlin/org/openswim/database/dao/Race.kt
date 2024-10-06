package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Races : IntIdTable("races") {
    val distance = integer("distance")
    val style = varchar("style", 255)
    val categoryId = reference("category_id", Categories, onDelete = ReferenceOption.CASCADE)
    val competitionId = reference("competition_id", Competitions, onDelete = ReferenceOption.CASCADE)
}

class Race(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Race>(Races)

    var distance by Races.distance
    var style by Races.style
    var category by Category referencedOn Races.categoryId
    var competition by Competition referencedOn Races.competitionId
    val subscriptions by RaceSubscription referrersOn RaceSubscriptions.raceId

}




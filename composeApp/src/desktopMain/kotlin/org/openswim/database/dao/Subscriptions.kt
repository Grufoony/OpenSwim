package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Subscriptions : IntIdTable("subscriptions") {
    val athleteId = reference("athlete_id", Athletes, onDelete = ReferenceOption.CASCADE)
    val raceId = reference("race_id", Competitions, onDelete = ReferenceOption.CASCADE)
    val time = long("time").nullable()
}

class Subscription(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Subscription>(Subscriptions)

    var athlete by Athlete referencedOn Subscriptions.athleteId
    var race by Race referencedOn Races.categoryId
    var time by Subscriptions.time
}


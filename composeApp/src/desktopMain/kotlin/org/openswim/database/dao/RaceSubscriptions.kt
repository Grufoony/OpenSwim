package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object RaceSubscriptions : IntIdTable("race_subscriptions") {
    val athleteId = reference("athlete_id", Athletes, onDelete = ReferenceOption.CASCADE)
    val raceId = reference("race_id", Competitions, onDelete = ReferenceOption.CASCADE)
    val time = long("time").nullable()
}

class RaceSubscription(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RaceSubscription>(RaceSubscriptions)

    var athlete by Athlete referencedOn RaceSubscriptions.athleteId
    var race by Race referencedOn RaceSubscriptions.raceId
    var time by RaceSubscriptions.time
}


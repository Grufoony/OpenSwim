package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object RelaySubscriptionTimes : IntIdTable("relay_sub_times") {
    val relaySubscriptionId =
        reference("relay_sub_id", Relays.id, onDelete = ReferenceOption.CASCADE, onUpdate = ReferenceOption.CASCADE)
    val athleteId = reference(
        "athlete_id",
        RelayTeamAthletes.athleteId,
        onDelete = ReferenceOption.RESTRICT,
        onUpdate = ReferenceOption.CASCADE
    )
    val time = integer("time")
}

class RelaySubscriptionTime(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RelaySubscriptionTime>(RelaySubscriptionTimes)

    var relaySubscription by RelaySubscription referencedOn RelaySubscriptionTimes.relaySubscriptionId
    var athlete by Athlete referencedOn RelaySubscriptionTimes.athleteId
    var time by RelaySubscriptionTimes.time
}
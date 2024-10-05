package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Table

object RelaySubscriptionTimes : IntIdTable("relay_sub_times") {
    val relaySubscriptionId = reference("relay_sub_id", Relays.id)
    val athlete_id = reference("athlete_id", Athletes.id)
    val time = integer("time")
}

class RelaySubscriptionTime(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RelaySubscriptionTime>(RelaySubscriptionTimes)

    var relaySubscription by RelaySubscription referencedOn RelaySubscriptionTimes.relaySubscriptionId
    var athlete by Athlete referencedOn RelaySubscriptionTimes.athlete_id
    var time by RelaySubscriptionTimes.time
}
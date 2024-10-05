package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object RelaySubscriptions : IntIdTable("relay_subscriptions") {
    val relayTeamId = reference("relay_team_id", RelayTeams.id)
    val relayId = reference("relay_id", Relays.id)
    val total_time = integer("total_time")
}

class RelaySubscription(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RelaySubscription>(RelaySubscriptions)

    var relayTeam by RelayTeam referencedOn RelaySubscriptions.relayTeamId
    var relay by Relay referencedOn RelaySubscriptions.relayId
    var totalTime by RelaySubscriptions.total_time
}
package org.openswim.database.dao

import org.jetbrains.exposed.sql.Table

object RelayTeamAthletes : Table("relay_team_athletes") {
    val athleteId = reference("athlete_id", Athletes.id)
    val relayTeamId = reference("relay_team_id", RelayTeams.id)

    init {
        uniqueIndex(athleteId, relayTeamId)
    }
}
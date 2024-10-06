package org.openswim.database.dao

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object RelayTeamAthletes : Table("relay_team_athletes") {
    val relayTeamId = reference(
        "relay_team_id",
        RelayTeams.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val athleteId =
        reference("athlete_id", Athletes.id, onDelete = ReferenceOption.RESTRICT, onUpdate = ReferenceOption.CASCADE)

    init {
        uniqueIndex(athleteId, relayTeamId)
    }
}
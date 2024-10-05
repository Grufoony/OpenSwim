package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.VarCharColumnType

object RelayTeams : IntIdTable("relay_teams") {
    val name = varchar("name", VarCharColumnType().colLength)
}

class RelayTeam(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<RelayTeam>(RelayTeams)

    var name by RelayTeams.name
    var athletes by Athlete via RelayTeamAthletes
}


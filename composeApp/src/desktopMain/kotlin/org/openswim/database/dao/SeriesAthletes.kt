package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object SeriesAthletes : Table("series_athletes") {
    val series_id = reference("series_id", Series, onDelete = ReferenceOption.CASCADE)
    val athlete_id = reference("athlete_id", Athletes, onDelete = ReferenceOption.CASCADE)

    init {
        uniqueIndex(series_id, athlete_id)
    }
}

//class SeriesAthlete(id: EntityID<Int>) : IntEntity(id) {
//    companion object : IntEntityClass<SeriesAthlete>(SeriesAthletes)
//
//    var series by Serie referencedOn SeriesAthletes.series_id
//    var athlete by Athlete referencedOn SeriesAthletes.athlete_id
//
//}
package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption

object Series : IntIdTable("series") {
    val seriesName = varchar("series_name", 255)
    val raceId = reference("race_id", Athletes, onDelete = ReferenceOption.CASCADE)
}

class Serie(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Serie>(Series)

    var seriesName by Series.seriesName
    var race by Race referencedOn Series.raceId
    var athletes by Athlete via SeriesAthletes

}
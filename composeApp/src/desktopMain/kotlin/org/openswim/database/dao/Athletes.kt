package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.javatime.date

object Athletes : IntIdTable("athletes") {
    val name = varchar("name", 255)
    val surname = varchar("surname", 255)
    val sex = varchar("sex", 1)
    val birthDate = date("birthdate")
    val categoryId = reference("category_id", Categories, onDelete = ReferenceOption.CASCADE)
}

class Athlete(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Athlete>(Athletes)

    var name by Athletes.name
    var surname by Athletes.surname
    var sex by Athletes.sex
    var birthDate by Athletes.birthDate
    var category by Category referencedOn Athletes.categoryId
}


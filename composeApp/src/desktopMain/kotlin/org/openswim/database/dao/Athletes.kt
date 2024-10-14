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
    val cf = varchar("cf", 16).nullable()
    val fin_code = varchar("fin_code", 50).nullable()
    val uisp_code = varchar("uisp_code", 50).nullable()
    val country = varchar("country", 50).nullable()
    val email = varchar("email", 255).nullable()
    val birthDate = date("birthdate")
    val clubId = reference("club_id", Clubs, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)
    val categoryId =
        reference("category_id", Categories, onDelete = ReferenceOption.SET_NULL, onUpdate = ReferenceOption.CASCADE)
}

class Athlete(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Athlete>(Athletes)

    var name by Athletes.name
    var surname by Athletes.surname
    var sex by Athletes.sex
    var cf by Athletes.cf
    var finCode by Athletes.fin_code
    var uispCode by Athletes.uisp_code
    var country by Athletes.country
    var email by Athletes.email
    var birthDate by Athletes.birthDate
    var club by Club referencedOn Athletes.clubId
    var category by Category referencedOn Athletes.categoryId
}


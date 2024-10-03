package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Categories : IntIdTable("categories") {
    val code = varchar("code", 10)
    val name = varchar("name", 50)
    val sex = varchar("sex", 1).check { it inList listOf("M", "F") }
    val minAge = integer("min_age")
    val maxAge = integer("max_age")
    val minYear = integer("min_year")
    val maxYear = integer("max_year")
    val custom = bool("custom").default(false)
}

class Category(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Category>(Categories)

    var code by Categories.code
    var name by Categories.name
    var sex by Categories.sex
    var minAge by Categories.minAge
    var maxAge by Categories.maxAge
    var minYear by Categories.minYear
    var maxYear by Categories.maxYear
    var custom by Categories.custom
}
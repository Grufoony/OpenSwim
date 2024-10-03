package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Addresses : IntIdTable("addresses") {
    val street = varchar("street", 255)
    val number = varchar("number", 50)
    val city = varchar("city", 255)
    val state = varchar("state", 255)
    val country = varchar("country", 255)
}

class Address(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Address>(Addresses)

    var street by Addresses.street
    var number by Addresses.number
    var city by Addresses.city
    var state by Addresses.state
    var country by Addresses.country
}
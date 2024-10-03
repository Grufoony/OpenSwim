package org.openswim.database.dao

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object Chronos : IntIdTable("chronos") {
    val name = varchar("name", 255)
    val version = varchar("version", 255)
    val serialBaud = integer("serial_baud")
    val serialCharSize = integer("serial_char_size")
    val serialParity = varchar("serial_parity", 10).check{ it inList setOf("none", "even", "odd", "mark") }
    val serialStopBits = integer("serial_stopbits")
    val protocolDesc = varchar("protocol_desc", 255)
}

class Chrono(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<Chrono>(Chronos)

    var name by Chronos.name
    var version by Chronos.version
    var serialBaud by Chronos.serialBaud
    var serialCharSize by Chronos.serialCharSize
    var serialParity by Chronos.serialParity
    var serialStopBits by Chronos.serialStopBits
    var protocolDesc by Chronos.protocolDesc
}
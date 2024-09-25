import org.jetbrains.exposed.sql.Table

const val MAX_VARCHAR_LENGTH = 64

object Athletes : Table("athletes") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", MAX_VARCHAR_LENGTH)
    val surname = varchar("surname", MAX_VARCHAR_LENGTH)
    val sex = varchar("sex", 1).check { it inList listOf("M", "F") }
}
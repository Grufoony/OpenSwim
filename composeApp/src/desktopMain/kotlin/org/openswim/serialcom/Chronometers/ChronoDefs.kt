package org.openswim

sealed interface ChronoDefs {
    val name: String
    val end_cmd: String
    val version: String
    val next_msg: String
    val sep: Char
    val id_name: String
}
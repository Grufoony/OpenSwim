package org.openswim

class Master : ChronoDefs {
    override val name = "Digitech Master"
    override val end_cmd = "\r\n"
    override val version = "+V"
    override val next_msg = "+D"
    override val sep = '-'
    override val id_name = "\$MASTA" // To check
}
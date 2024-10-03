package org.openswim.database.dao

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object CommitteesJudges : Table("committees_judges") {
    val committeeId = reference("committee_id", Committees, onDelete = ReferenceOption.CASCADE)
    val judgeId = reference("judge_id", Judges, onDelete = ReferenceOption.CASCADE)
}
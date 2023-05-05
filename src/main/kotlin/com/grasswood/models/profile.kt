package com.grasswood.models

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

// Declares a Profile object corresponding to a Profile table that extends `org.jetbrains.exposed.sql.Table`.
object Profile : Table("profile") {
    val id: Column<Int> = integer("id").autoIncrement()
    val email = varchar("email", 100)
    val password = varchar("password", 100)
    override val primaryKey = PrimaryKey(id, name = "PK_Profile_ID")
}

// Declares a ProfileType data class.
data class ProfileType(
    val id: Int,
    val email: String,
    val password: String
)
package r2dbc.kotlin.model

import org.springframework.data.relational.core.mapping.Table

@Table("person")
data class Person(val id: Long, val name: String, val age: Int)